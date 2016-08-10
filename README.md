#[CMG Mobile Apps](http://www.cmgapps.com?utm_source=github&utm_medium=README&utm_campaign=default) Android&trade; Utilities
[![Maven Central](https://img.shields.io/maven-central/v/com.cmgapps.android/cmgUtilities.svg)](https://oss.sonatype.org/content/repositories/releases/com/cmgapps/android/cmgUtilities/)
[![Bintray](https://img.shields.io/bintray/v/bintray/jcenter/com.cmgapps.android:cmgUtilities.svg)](https://jcenter.bintray.com/com/cmgapps/android/cmgUtilities/)
This is a collection of Android Utility classes.

They are used at my Android&trade; Projects:

* [Bierdeckel][1]
* [PhoNews][2]
* [PhoNews Pro][3]
* [Roman Numerals Converter][4]
* [Running Sushi][5]

Utilities
---------

1. [FlowLayout.java](https://github.com/chrimaeon/CMG-Android-Utilities/blob/master/library/src/main/java/com/cmgapps/android/layout/FlowLayout.java)

    A layout which arranges as many of its child views in a row as possible.

1. [AsyncTaskExecutionHelper.java](https://github.com/chrimaeon/CMG-Android-Utilities/blob/master/library/src/main/java/com/cmgapps/android/util/AsyncTaskExecutionHelper.java)

    Execute your AsyncTask in parallel or serial.

1. [LogUtils.java](https://github.com/chrimaeon/CMG-Android-Utilities/blob/master/library/src/main/java/com/cmgapps/android/util/LogUtils.java)

    Log helper class.

1. [PreferenceEditorHelper.java](https://github.com/chrimaeon/CMG-Android-Utilities/blob/master/library/src/main/java/com/cmgapps/android/util/PreferenceEditorHelper.java)

    Commit your changes to a SharedPreference file asynchronously.

1. [UiUtilities.java](https://github.com/chrimaeon/CMG-Android-Utilities/blob/master/library/src/main/java/com/cmgapps/android/util/UiUtilities.java)

    Helps you retrieving Views from ViewGroups or Activities.

1. [ApiUtils.java](https://github.com/chrimaeon/CMG-Android-Utilities/blob/master/library/src/main/java/com/cmgapps/android/util/ApiUtils.java)

	  Utility class for checking Android API Version

1. [BitmapCache.java](https://github.com/chrimaeon/CMG-Android-Utilities/blob/master/library/src/main/java/com/cmgapps/android/util/BitmapCache.java)

    Cache for bitmaps using Android's [LruCache](http://developer.android.com/reference/android/util/LruCache.html)

Gradle
------

Android Utilities are now pushed to Maven Central as an AAR, so you just need to add the following dependency to your `build.gradle`.

    dependencies {
        compile 'com.cmgapps.android:cmgUtilities:1.0'
    }

Developed By
------------

* Christian Grach - <christian.grach@cmgapps.com>
* et al.

License
-------

    Copyright 2013-2015 Christian Grach

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

*Android is a trademark of Google Inc.*

 [1]: https://play.google.com/store/apps/details?id=com.cmgapps.android.bierdeckel&referrer=utm_source%3Dgithub%26utm_medium%3DREADME
 [2]: https://play.google.com/store/apps/details?id=at.cmg.android.phonews&referrer=utm_source%3Dgithub%26utm_medium%3DREADME
 [3]: https://play.google.com/store/apps/details?id=com.cmgapps.android.phonewspro&referrer=utm_source%3Dgithub%26utm_medium%3DREADME
 [4]: https://play.google.com/store/apps/details?id=com.cmgapps.android.numeralsconverter&referrer=utm_source%3Dgithub%26utm_medium%3DREADME
 [5]: https://play.google.com/store/apps/details?id=com.cmgapps.android.sushicounter&referrer=utm_source%3Dgithub%26utm_medium%3DREADME
