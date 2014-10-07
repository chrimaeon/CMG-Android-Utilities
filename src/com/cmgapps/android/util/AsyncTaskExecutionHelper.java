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

import java.util.concurrent.Executor;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

/**
 * <p>Uses level 11 APIs (Honeycomb) when possible to use parallel/serial executors and falls
 * back to standard execution if API level is below 11.</p>
 * 
 */
public class AsyncTaskExecutionHelper
{
  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  static class HoneycombExecutionHelper
  {
    public static <P> void execute(AsyncTask<P, ?, ?> asyncTask, boolean parallel, P... params)
    {
      Executor executor = parallel ? AsyncTask.THREAD_POOL_EXECUTOR : AsyncTask.SERIAL_EXECUTOR;
      asyncTask.executeOnExecutor(executor, params);
    }
  }

  /**
   * <p>Executes the {@link AsyncTask} in parallel</p>
   * @param asyncTask the <code>AsyncTask</code> to execute
   * @param params the params to pass to the <code>AsyncTask</code>
   */
  public static <P> void executeParallel(AsyncTask<P, ?, ?> asyncTask, P... params)
  {
    execute(asyncTask, true, params);
  }

  /**
   * <p>Executes the {@link AsyncTask} in serial</p>
   * @param asyncTask the <code>AsyncTask</code> to execute
   * @param params the params to pass to the <code>AsyncTask</code>
   */
  public static <P> void executeSerial(AsyncTask<P, ?, ?> asyncTask, P... params)
  {
    execute(asyncTask, false, params);
  }

  private static <P> void execute(AsyncTask<P, ?, ?> asyncTask, boolean parallel, P... params)
  {
    if (ApiUtils.hasHoneycomb())
    {
      HoneycombExecutionHelper.execute(asyncTask, parallel, params);
    }
    else
    {
      asyncTask.execute(params);
    }
  }
}