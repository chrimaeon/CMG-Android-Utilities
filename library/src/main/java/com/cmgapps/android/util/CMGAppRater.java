/*
 * Copyright 2013 Christian Grach
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cmgapps.android.util;

import android.app.Activity;
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
import android.os.Bundle;
import android.os.Looper;
import android.text.format.DateUtils;

import com.cmgapps.android.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.cmgapps.android.util.LogUtils.LOGE;
import static com.cmgapps.android.util.LogUtils.LOGI;
import static com.cmgapps.android.util.LogUtils.makeLogTag;

/**
 * Class that utilizes usage count and time to open a rate dialog.
 * <p>
 * Use {@link #incrementUseCount()} on your main activity
 * {@link Activity#onCreate(Bundle)} implementation.
 * <p>
 * Then call {@link #checkForRating()} to check if the requirements are met to
 * show the dialog and finally call {@link #show(Context)} to show the rating dialog
 */
public class CMGAppRater {
    private static final String TAG = makeLogTag("CMGAppRater");
    private static final String APP_RATE_FILE_NAME = "CMGAppRater";
    private static final int LAUNCHES_UNTIL_PROMPT = 5;
    private static final long DAYS_UNTIL_PROMPT = 5 * DateUtils.DAY_IN_MILLIS;
    private static final long DAYS_UNTIL_REMIND_AGAIN = 2 * DateUtils.DAY_IN_MILLIS;

    private static final String FIRST_USE = "first_use";
    private static final String USE_COUNT = "use_count";
    private static final String DECLINED_RATE = "declined_rate";
    private static final String TRACKING_VERSION = "tracking_version";
    private static final String REMIND_LATER_DATE = "remind_later_date";
    private static final String APP_RATED = "rated";
    private static final boolean RATER_DEBUG = false;

    private SharedPreferences mPref;
    private Context mContext;
    private boolean mDebug = false;
    private AlertDialog mDialog;
    private int mLaunchesUntilPrompt;
    private long mDaysUntilPrompt;
    private long mDaysUntilRemindAgain;

    private static CMGAppRater sInstance = null;

    private CMGAppRater(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }

        mContext = context.getApplicationContext();
        mPref = mContext.getSharedPreferences(APP_RATE_FILE_NAME, Context.MODE_PRIVATE);
        mLaunchesUntilPrompt = LAUNCHES_UNTIL_PROMPT;
        mDaysUntilPrompt = DAYS_UNTIL_PROMPT;
        mDaysUntilRemindAgain = DAYS_UNTIL_REMIND_AGAIN;
    }

    /**
     * Get a {@link CMGAppRater} instance
     *
     * @param context the Application Context
     * @return The {@link CMGAppRater} instance
     */
    public static CMGAppRater getInstance(Context context) {
        synchronized (CMGAppRater.class) {
            if (sInstance == null) {
                sInstance = new CMGAppRater(context);
            }

            return sInstance;
        }
    }

    /**
     * @param launchesUntilPrompt the launchesUntilPrompt to set
     */
    public void setLaunchesUntilPrompt(int launchesUntilPrompt) {
        mLaunchesUntilPrompt = launchesUntilPrompt;
    }

    /**
     * @param daysUntilPrompt the daysUntilPrompt to set
     */
    public void setDaysUntilPrompt(long daysUntilPrompt) {
        mDaysUntilPrompt = daysUntilPrompt;
    }

    /**
     * @param daysUntilRemindAgain the daysUntilRemindAgain to set
     */
    public void setDaysUntilRemindAgain(long daysUntilRemindAgain) {
        mDaysUntilRemindAgain = daysUntilRemindAgain;
    }

    /**
     * Sets the debug flag to display current <code>CmgAppRater</code> field
     * values on {@link #checkForRating()}
     *
     * @param debug true to display debug output
     */
    public void setDebug(boolean debug) {
        mDebug = debug;
    }

    /**
     * Call to check if the requirements to open the rating dialog are met
     * <p>
     * <b>NOTICE:</b> This method is thread safe
     *
     * @return true if requirements are met.
     */
    public synchronized boolean checkForRating() {

        if (mDebug)
            LOGI(TAG, "Rater Content:" + toString());

        if (RATER_DEBUG)
            return true;

        if (mPref.getBoolean(DECLINED_RATE, false))
            return false;

        if (mPref.getBoolean(APP_RATED, false))
            return false;

        if (System.currentTimeMillis() < (mPref.getLong(FIRST_USE, 0L) + mDaysUntilPrompt))
            return false;

        if (mPref.getInt(USE_COUNT, 0) <= mLaunchesUntilPrompt)
            return false;

        if (System.currentTimeMillis() < (mPref.getLong(REMIND_LATER_DATE, 0L) + mDaysUntilRemindAgain))
            return false;

        return true;
    }

    /**
     * Increments the usage count.
     * <p>
     * <b>NOTICE:</b> This method is thread safe
     */
    public synchronized void incrementUseCount() {

        Editor editor = mPref.edit();
        int version_code = 0;

        try {
            version_code = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException exc) {
            LOGE(TAG, "PackageName not found: " + mContext.getPackageName());
        }

        int tracking_version = mPref.getInt(TRACKING_VERSION, -1);

        if (tracking_version == -1) {
            tracking_version = version_code;
            editor.putInt(TRACKING_VERSION, tracking_version);
        }

        if (tracking_version == version_code) {

            if (mPref.getLong(FIRST_USE, 0L) == 0L)
                editor.putLong(FIRST_USE, System.currentTimeMillis());

            editor.putInt(USE_COUNT, mPref.getInt(USE_COUNT, 0) + 1);
        } else {
            editor.putInt(TRACKING_VERSION, version_code).putLong(FIRST_USE, System.currentTimeMillis()).putInt(USE_COUNT, 1)
                    .putBoolean(DECLINED_RATE, false).putLong(REMIND_LATER_DATE, 0L).putBoolean(APP_RATED, false);
        }

        PreferenceEditorHelper.commit(editor);
    }

    /**
     * Shows a default {@link AlertDialog}
     *
     * @param context A Context to show the dialog
     */
    public void show(final Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }

        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            throw new RuntimeException("CMGAppRater.show() must be called from main thread");
        }

        if (mDialog != null && mDialog.isShowing())
            return;

        final Editor editor = mPref.edit();
        final PackageManager pm = context.getPackageManager();

        String appName;
        try {
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), 0);
            appName = (String) pm.getApplicationLabel(ai);
        } catch (NameNotFoundException e) {
            LOGE(TAG, "Application name can not be found");
            appName = "App";
        }

        mDialog = new AlertDialog.Builder(context).setTitle(R.string.dialog_cmgrate_title)
                .setMessage(context.getString(R.string.dialog_cmgrate_message, appName)).setCancelable(false)
                .setIcon(context.getApplicationInfo().icon)
                .setPositiveButton(context.getString(R.string.dialog_cmgrate_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        editor.putBoolean(APP_RATED, true);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        PreferenceEditorHelper.commit(editor);
                        dialog.dismiss();
                    }
                }).setNegativeButton(R.string.dialog_cmgrate_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        PreferenceEditorHelper.commit(editor.putBoolean(DECLINED_RATE, true));
                        dialog.dismiss();
                    }
                }).setNeutralButton(R.string.dialog_cmgrate_later, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        PreferenceEditorHelper.commit(editor.putLong(REMIND_LATER_DATE, System.currentTimeMillis()));
                        dialog.dismiss();
                    }
                }).show();
        PreferenceEditorHelper.commit(editor);
    }

    private static String ratePreferenceToString(SharedPreferences pref) {
        JSONObject thiz = new JSONObject();
        try {
            thiz.put(DECLINED_RATE, pref.getBoolean(DECLINED_RATE, false));
            thiz.put(APP_RATED, pref.getBoolean(APP_RATED, false));
            thiz.put(TRACKING_VERSION, pref.getInt(TRACKING_VERSION, -1));
            thiz.put(FIRST_USE, SimpleDateFormat.getDateTimeInstance().format(new Date(pref.getLong(FIRST_USE, 0L))));
            thiz.put(USE_COUNT, pref.getInt(USE_COUNT, 0));
            thiz.put(REMIND_LATER_DATE,
                    SimpleDateFormat.getDateTimeInstance().format(new Date(pref.getLong(REMIND_LATER_DATE, 0L))));
        } catch (JSONException exc) {
            LOGE(TAG, "Error creating JSON Object", exc);
        }

        return thiz.toString();
    }

    /**
     * Get the {@link SharedPreferences} file contents
     */
    @Override
    public String toString() {
        return ratePreferenceToString(mPref);
    }
}
