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
public class ApiUtils {

    private ApiUtils() {
        // Utility Class
    }

    /**
     * Checks if build version is {@link Build.VERSION_CODES#GINGERBREAD GINGERBREAD}
     *
     * @return true if build version is &gt;= API {@value Build.VERSION_CODES#GINGERBREAD}
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * Checks if build version is {@link Build.VERSION_CODES#GINGERBREAD_MR1 GINGERBREAD_MR1}
     *
     * @return true if build version is &gt;= API {@value Build.VERSION_CODES#GINGERBREAD_MR1}
     */
    public static boolean hasGingerbreadMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1;
    }

    /**
     * Checks if build version is {@link Build.VERSION_CODES#HONEYCOMB HONEYCOMB}
     *
     * @return true if build version is &gt;= API {@value Build.VERSION_CODES#HONEYCOMB}
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * Checks if build version is {@link Build.VERSION_CODES#HONEYCOMB_MR1 HONEYCOMB_MR1}
     *
     * @return true if build version is &gt;= API {@value Build.VERSION_CODES#HONEYCOMB_MR1}
     */
    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * Checks if build version is {@link Build.VERSION_CODES#HONEYCOMB_MR2 HONEYCOMB_MR2}
     *
     * @return true if build version is &gt;= API {@value Build.VERSION_CODES#HONEYCOMB_MR2}
     */
    public static boolean hasHoneycombMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2;
    }

    /**
     * Checks if build version is {@link Build.VERSION_CODES#ICE_CREAM_SANDWICH ICE_CREAM_SANDWICH}
     *
     * @return true if build version is &gt;= API {@value Build.VERSION_CODES#ICE_CREAM_SANDWICH}
     */
    public static boolean hasIceCreamSandwitch() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * Checks if build version is {@link Build.VERSION_CODES#ICE_CREAM_SANDWICH_MR1 ICE_CREAM_SANDWICH_MR1}
     *
     * @return true if build version is &gt;= API {@value Build.VERSION_CODES#ICE_CREAM_SANDWICH_MR1}
     */
    public static boolean hasIceCreamSandwitchMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
    }

    /**
     * Checks if build version is {@link Build.VERSION_CODES#JELLY_BEAN JELLY_BEAN}
     *
     * @return true if build version is &gt;= API {@value Build.VERSION_CODES#JELLY_BEAN}
     */
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * Checks if build version is {@link Build.VERSION_CODES#JELLY_BEAN_MR1 JELLY_BEAN_MR1}
     *
     * @return true if build version is &gt;= API {@value Build.VERSION_CODES#JELLY_BEAN_MR1}
     */
    public static boolean hasJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * Checks if build version is {@link Build.VERSION_CODES#JELLY_BEAN_MR2 JELLY_BEAN_MR2}
     *
     * @return true if build version is &gt;= API {@value Build.VERSION_CODES#JELLY_BEAN_MR2}
     */
    public static boolean hasJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    /**
     * Checks if build version is {@link Build.VERSION_CODES#KITKAT KITKAT}
     *
     * @return true if build version is &gt;= API {@value Build.VERSION_CODES#KITKAT}
     */
    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * Checks if build version is {@link Build.VERSION_CODES#KITKAT_WATCH KITKAT_WATCH}
     *
     * @return true if build version is &gt;= API {@value Build.VERSION_CODES#KITKAT_WATCH}
     */
    public static boolean hasKitKatWatch() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH;
    }

    /**
     * Checks if build version is {@link Build.VERSION_CODES#LOLLIPOP LOLLIPOP}
     *
     * @return true if build version is &gt;= API {@value Build.VERSION_CODES#LOLLIPOP}
     */
    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Checks if build version is {@link Build.VERSION_CODES#LOLLIPOP_MR1 LOLLIPOP_MR1}
     *
     * @return true if build version is &gt;= API {@value Build.VERSION_CODES#LOLLIPOP_MR1}
     */
    public static boolean hasLollipopMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    /**
     * Checks if build version is {@link Build.VERSION_CODES#M VERSION_CODES.M}
     *
     * @return true if build version is &gt;= API {@value Build.VERSION_CODES#M}
     */
    public static boolean hasM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * Checks if build version is {@link Build.VERSION_CODES#M VERSION_CODES.N}
     *
     * @return true if build version is &gt;= API {@value Build.VERSION_CODES#N}
     */
    public static boolean hasN() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }
}
