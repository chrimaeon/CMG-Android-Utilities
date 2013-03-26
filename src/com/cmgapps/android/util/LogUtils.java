package com.cmgapps.android.util;

import android.util.Log;

import com.cmgapps.android.BuildConfig;

/**
 * Helper methods that make logging more consistent throughout the app.
 */
public class LogUtils
{

  public static void LOGD(final String tag, String message)
  {
    if (Log.isLoggable(tag, Log.DEBUG))
      Log.d(tag, message);
  }

  public static void LOGD(final String tag, String message, Throwable cause)
  {
    if (Log.isLoggable(tag, Log.DEBUG))
      Log.d(tag, message, cause);
  }

  public static void LOGV(final String tag, String message)
  {
    if (BuildConfig.DEBUG && Log.isLoggable(tag, Log.VERBOSE))
      Log.v(tag, message);
  }

  public static void LOGV(final String tag, String message, Throwable cause)
  {
    if (BuildConfig.DEBUG && Log.isLoggable(tag, Log.VERBOSE))
      Log.v(tag, message, cause);
  }

  public static void LOGI(final String tag, String message)
  {
    Log.i(tag, message);
  }

  public static void LOGI(final String tag, String message, Throwable cause)
  {
    Log.i(tag, message, cause);
  }

  public static void LOGW(final String tag, String message)
  {
    Log.w(tag, message);
  }

  public static void LOGW(final String tag, String message, Throwable cause)
  {
    Log.w(tag, message, cause);
  }

  public static void LOGE(final String tag, String message)
  {
    Log.e(tag, message);
  }

  public static void LOGE(final String tag, String message, Throwable cause)
  {
    Log.e(tag, message, cause);
  }

  private LogUtils()
  {
  }
}