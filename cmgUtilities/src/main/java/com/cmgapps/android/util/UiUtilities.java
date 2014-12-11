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
import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Helper class for retrieving a View from a layout
 */
public class UiUtilities {
    public static final int ANIMATION_FADE_IN_TIME = 250;
    public static final Interpolator QUINT_DECELERATE_INTERPOLATOR = new DecelerateInterpolator(2.5f);
    public static final Interpolator CUBIC_DECELERATE_INTERPOLATOR = new DecelerateInterpolator(1.5f);

    private UiUtilities() {
        // Utility class
    }

    /**
     * <p>
     * Gets a view from a {@link ViewGroup}
     * </p>
     *
     * @param parent     the view to look in
     * @param resourceId the id of the view to look for
     * @param <T>        type of the View
     * @return the view
     * @throws IllegalArgumentException if view with the given id does not exist
     */
    public static <T extends View> T getView(View parent, int resourceId) {
        return (T) checkView(parent.findViewById(resourceId));
    }

    /**
     * <p>
     * Gets a view from a {@link Activity}
     * </p>
     *
     * @param parent     the Activity to look in
     * @param resourceId the id of the view to look for
     * @param <T>        type of the View
     * @return the view
     * @throws IllegalArgumentException if view with the givven id does not exist
     */
    public static <T extends View> T getView(Activity parent, int resourceId) {
        return (T) checkView(parent.findViewById(resourceId));
    }

    /**
     * <p>
     * Gets a view from a {@link ViewGroup}
     * </p>
     *
     * @param parent     the view to look in
     * @param resourceId the id of the view to look for
     * @param <T>        type of the View
     * @return the view or null if the view does not exist
     */
    public static <T extends View> T getViewOrNull(View parent, int resourceId) {
        return (T) parent.findViewById(resourceId);
    }

    /**
     * <p>
     * Gets a view from a {@link Activity}
     * </p>
     *
     * @param parent     the Activity to look in
     * @param resourceId the id of the view to look for
     * @param <T>        type of the View
     * @return the view or null if the view does not exist
     */
    public static <T extends View> T getViewOrNull(Activity parent, int resourceId) {
        return (T) parent.findViewById(resourceId);
    }

    private static View checkView(View v) {
        if (v == null)
            throw new IllegalArgumentException("View does not exist");

        return v;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
