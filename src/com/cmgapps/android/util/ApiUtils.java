package com.cmgapps.android.util;

import android.os.Build;

public class ApiUtils
{
  private ApiUtils()
  {
    // Utility Class
  }

  /**
   * Checks if build version is Gingerbread MR1
   * 
   * @return true if build version is >= API 10
   */
  public static boolean hasGingerbreadMR1()
  {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1;
  }

  /**
   * Checks if build version is Honeycomb
   * 
   * @return true if build version is >= API 11
   */
  public static boolean hasHoneycomb()
  {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
  }

  /**
   * Checks if build version is Honeycomb MR1
   * 
   * @return true if build version is >= API 12
   */
  public static boolean hasHoneycombMR1()
  {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
  }
}
