package com.cmgapps.android.util;

import android.annotation.TargetApi;
import android.content.SharedPreferences.Editor;
import android.os.Build;

public class PreferenceEditorHelper
{
  @TargetApi(Build.VERSION_CODES.GINGERBREAD)
  public static void commit(Editor edit)
  {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
    {
      edit.apply();
    }
    else
    {
      edit.commit();
    }
  }

}
