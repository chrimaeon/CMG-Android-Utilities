package com.cmgapps.android.util;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.text.format.DateUtils;
import android.util.Log;

import com.cmgapps.android.R;

public class CMGAppRater
{
  private static final String TAG = "CMGAppRater";
  private static final String APP_RATE_FILE_NAME = "CMGAppRater";
  private static final int LAUNCHES_UNTIL_PROMPT = 10;
  private static final long DAYS_UNTIL_PROMPT = 7 * DateUtils.DAY_IN_MILLIS;
  private static final long DAYS_UNTIL_REMIND_AGAIN = 2 * DateUtils.DAY_IN_MILLIS;

  private static final String FIRST_USE = "first_use";
  private static final String USE_COUNT = "use_count";
  private static final String DECLINED_RATE = "declined_rate";
  private static final String TRACKING_VERSION = "tracking_version";
  private static final String REMIND_LATER_DATE = "remind_later_date";
  private static final String APP_RATED = "rated";
  private static final boolean RATER_DEBUG = false;

  private final SharedPreferences mPref;
  private final Context mContext;

  public CMGAppRater(Context context)
  {
    mContext = context;
    mPref = context.getSharedPreferences(APP_RATE_FILE_NAME, Context.MODE_PRIVATE);
  }

  public synchronized boolean checkForRating()
  {
    if (RATER_DEBUG)
      return true;

    if (mPref.getBoolean(DECLINED_RATE, false))
      return false;

    if (mPref.getBoolean(APP_RATED, false))
      return false;

    if (System.currentTimeMillis() < (mPref.getLong(FIRST_USE, System.currentTimeMillis()) + DAYS_UNTIL_PROMPT))
      return false;

    if (mPref.getInt(USE_COUNT, 0) <= LAUNCHES_UNTIL_PROMPT)
      return false;

    if (System.currentTimeMillis() < (mPref.getLong(REMIND_LATER_DATE, System.currentTimeMillis()) + DAYS_UNTIL_REMIND_AGAIN))
      return false;

    return true;
  }

  public synchronized void incrementUseCount()
  {
    Editor editor = mPref.edit();
    int version_code = 0;

    try
    {
      version_code = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
    }
    catch (NameNotFoundException exc)
    {
      Log.e(TAG, "PackageName not found: " + mContext.getPackageName());
    }

    int tracking_version = mPref.getInt(TRACKING_VERSION, -1);

    if (tracking_version == -1)
    {
      tracking_version = version_code;
      editor.putInt(TRACKING_VERSION, tracking_version);
    }

    if (tracking_version == version_code)
    {

      if (mPref.getLong(FIRST_USE, 0L) == 0L)
        editor.putLong(FIRST_USE, System.currentTimeMillis());

      editor.putInt(USE_COUNT, mPref.getInt(USE_COUNT, 0) + 1);
    }
    else
    {
      editor.putInt(TRACKING_VERSION, version_code).putLong(FIRST_USE, System.currentTimeMillis()).putInt(USE_COUNT, 1)
          .putBoolean(DECLINED_RATE, false).putLong(REMIND_LATER_DATE, 0L).putBoolean(APP_RATED, false);
    }

    PreferenceEditorHelper.commit(editor);
  }

  @SuppressLint("StringFormatMatches")
  public void show()
  {
    final Editor editor = mPref.edit();
    final PackageManager pm = mContext.getPackageManager();

    String appName = null;
    try
    {
      ApplicationInfo ai = pm.getApplicationInfo(mContext.getPackageName(), 0);
      appName = (String) pm.getApplicationLabel(ai);
    }
    catch (final NameNotFoundException e)
    {
      Log.e(TAG, "Application with the package name '" + mContext.getPackageName() + "' can not be found");
      appName = "";
    }

    new AlertDialog.Builder(mContext)
        .setTitle(R.string.dialog_cmgrate_title)
        .setMessage(mContext.getString(R.string.dialog_cmgrate_message, appName))
        .setCancelable(false)
        .setIcon(mContext.getApplicationInfo().icon)
        .setPositiveButton(mContext.getString(R.string.dialog_cmgrate_ok, appName),
            new DialogInterface.OnClickListener()
            {
              @Override
              public void onClick(DialogInterface dialog, int id)
              {
                editor.putBoolean(APP_RATED, true);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="
                    + mContext.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

                PreferenceEditorHelper.commit(editor);
                dialog.dismiss();
              }
            }).setNegativeButton(R.string.dialog_cmgrate_no, new DialogInterface.OnClickListener()
        {
          @Override
          public void onClick(DialogInterface dialog, int id)
          {
            PreferenceEditorHelper.commit(editor.putBoolean(DECLINED_RATE, true));
            dialog.dismiss();
          }
        }).setNeutralButton(R.string.dialog_cmgrate_later, new DialogInterface.OnClickListener()
        {
          @Override
          public void onClick(DialogInterface dialog, int id)
          {
            PreferenceEditorHelper.commit(editor.putLong(REMIND_LATER_DATE, System.currentTimeMillis()));
            dialog.dismiss();
          }
        }).show();
    PreferenceEditorHelper.commit(editor);
  }

  private static String ratePreferenceToString(SharedPreferences pref)
  {
    StringBuilder builder = new StringBuilder("CMG App Rater Preferences: ");
    builder.append(DECLINED_RATE).append(": ").append(pref.getBoolean(DECLINED_RATE, false)).append(",\n");
    builder.append(APP_RATED).append(": ").append(pref.getBoolean(APP_RATED, false)).append(",\n");
    builder.append(TRACKING_VERSION).append(": ").append(pref.getInt(TRACKING_VERSION, -1)).append(",\n");
    builder.append(FIRST_USE).append(": ").append(pref.getLong(FIRST_USE, 0L)).append(",\n");
    builder.append(USE_COUNT).append(": ").append(pref.getInt(USE_COUNT, 0)).append(",\n");
    builder.append(REMIND_LATER_DATE).append(": ").append(pref.getLong(REMIND_LATER_DATE, 0));
    return builder.toString();
  }

  @Override
  public String toString()
  {
    return ratePreferenceToString(mPref);
  }
}
