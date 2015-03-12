/*
 * Copyright (c) 2013. Christian Grach <christian.grach@cmgapps.com>
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

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

/**
 * <p>
 * {@link SharedPreferences.Editor} helper method to utilize the new
 * {@link SharedPreferences.Editor#apply()} if running on a device &gt;=
 * API level 9 (Gingerbread)
 * </p>
 */
public class PreferenceEditorHelper {
    /**
     * Call with your {@link Editor} to apply/commit your changes.
     *
     * @param edit Your {@link SharedPreferences.Editor} object.
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void commit(Editor edit) {
        if (ApiUtils.hasGingerbread())
            edit.apply();
        else
            edit.commit();
    }

    private PreferenceEditorHelper() {
        // Utility class
    }

}
