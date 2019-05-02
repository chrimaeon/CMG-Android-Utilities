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

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

import java.util.concurrent.Executor;

/**
 * Uses level 11 APIs (Honeycomb) when possible to use parallel/serial executors and falls
 * back to standard execution if API level is below 11.
 */
@Deprecated
public class AsyncTaskExecutionHelper {
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    static class HoneycombExecutionHelper {
        @SafeVarargs
        public static <P> void execute(AsyncTask<P, ?, ?> asyncTask, boolean parallel, P... params) {
            Executor executor = parallel ? AsyncTask.THREAD_POOL_EXECUTOR : AsyncTask.SERIAL_EXECUTOR;
            asyncTask.executeOnExecutor(executor, params);
        }
    }

    /**
     * Executes the {@link AsyncTask} in parallel
     *
     * @param asyncTask the <code>AsyncTask</code> to execute
     * @param params    the params to pass to the <code>AsyncTask</code>
     * @param <P>       type of the parameters
     */
    @SafeVarargs
    public static <P> void executeParallel(AsyncTask<P, ?, ?> asyncTask, P... params) {
        execute(asyncTask, true, params);
    }

    /**
     * Executes the {@link AsyncTask} in serial
     *
     * @param asyncTask the <code>AsyncTask</code> to execute
     * @param params    the params to pass to the <code>AsyncTask</code>
     * @param <P>       type of the parameters
     */
    @SafeVarargs
    public static <P> void executeSerial(AsyncTask<P, ?, ?> asyncTask, P... params) {
        execute(asyncTask, false, params);
    }

    @SafeVarargs
    private static <P> void execute(AsyncTask<P, ?, ?> asyncTask, boolean parallel, P... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            HoneycombExecutionHelper.execute(asyncTask, parallel, params);
        } else {
            asyncTask.execute(params);
        }
    }
}
