/*
 * Copyright 2014 Christian Grach
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

import android.os.Build;

/**
 * Helper class for API version checks
 */
public class ApiUtils
{
  private ApiUtils()
  {
    // Utility Class
  }

  /**
   * <p>Checks if build version is Gingerbread MR1</p>
   * 
   * @return true if build version is &gt;= API 10
   */
  public static boolean hasGingerbreadMR1()
  {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1;
  }

  /**
   * <p>Checks if build version is Honeycomb</p>
   * 
   * @return true if build version is &gt;= API 11
   */
  public static boolean hasHoneycomb()
  {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
  }

  /**
   * <p>Checks if build version is Honeycomb MR1</p>
   * 
   * @return true if build version is &gt;= API 12
   */
  public static boolean hasHoneycombMR1()
  {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
  }

  /**
   * <p>Checks if build version is Ice Cream Sandwich</p>
   * 
   * @return true if build version is &gt;= API 14
   */
  public static boolean hasIceCreamSandwitch()
  {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
  }

  /**
   * <p>Checks if build version is Jelly Bean</p>
   * 
   * @return true if build version is &gt;= API 16
   */
  public static boolean hasJellyBean()
  {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
  }

  /**
   * <p>Checks if build version is KitKat</p>
   * 
   * @return true if build version is &gt;= API 19
   */
  public static boolean hasKitKat()
  {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
  }

  /**
   * <p>Checks if build version is Lollipop</p>
   * 
   * @return true if build version is &gt;= API 21
   */
  public static boolean hasLollipop()
  {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
  }
}
