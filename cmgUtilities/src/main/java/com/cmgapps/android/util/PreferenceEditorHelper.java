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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
            edit.apply();
        else
            edit.commit();
    }

    private PreferenceEditorHelper() {
        // Utility class
    }

}
