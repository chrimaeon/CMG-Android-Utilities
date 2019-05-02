/*
 * Copyright (c) 2015. Christian Grach <christian.grach@cmgapps.com>
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

import java.util.Properties

plugins {
    id("com.android.library")
    id("digital.wup.android-maven-publish") version "3.6.2"
    signing
}

android {
    compileSdkVersion(28)
    buildToolsVersion("28.0.3")

    defaultConfig {
        minSdkVersion(14)
        targetSdkVersion(28)
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("com.android.support:support-v4:28.0.0")
}

val POM_NAME: String by project
val VERSION_NAME: String by project

tasks.register<Javadoc>("androidJavadocs") {
    options {
        windowTitle = "${POM_NAME} v${VERSION_NAME} API"
    }
    source = android.sourceSets["main"].java.sourceFiles
    classpath += files(android.getBootClasspath().joinToString(File.pathSeparator))
    exclude("**/BuildConfig.java", "**/R.java")
}

tasks.register<Jar>("androidJavadocsJar") {
    archiveClassifier.set("javadoc")
    from(tasks["androidJavadocs"])
}

tasks.register<Jar>("androidSourcesJar") {
    archiveClassifier.set("sources")
    from(android.sourceSets["main"].java.srcDirs)
}

afterEvaluate {
    android.libraryVariants.forEach() { variant ->
        tasks["androidJavadocs"].doFirst {
            this as Javadoc
            classpath += files(variant.javaCompileProvider.get().classpath.files)
        }
    }
}

val GROUP: String by project
val POM_ARTIFACT_ID: String by project
val POM_PACKAGING: String by project
val POM_DESCRIPTION: String by project
val POM_URL: String by project
val POM_LICENCE_NAME: String by project
val POM_LICENCE_URL: String by project
val POM_LICENCE_DIST: String by project
val POM_DEVELOPER_ID: String by project
val POM_DEVELOPER_NAME: String by project
val POM_SCM_URL: String by project
val POM_SCM_CONNECTION: String by project
val POM_SCM_DEV_CONNECTION: String by project

publishing {
    publications {
        create<MavenPublication>("library") {

            from(components["android"])
            artifact(tasks["androidSourcesJar"])
            artifact(tasks["androidJavadocsJar"])

            groupId = GROUP
            artifactId = POM_ARTIFACT_ID
            version = VERSION_NAME

            pom {
                name.set(POM_NAME)
                packaging = POM_PACKAGING
                description.set(POM_DESCRIPTION)
                url.set(POM_URL)

                licenses {
                    license {
                        name.set(POM_LICENCE_NAME)
                        url.set(POM_LICENCE_URL)
                        distribution.set(POM_LICENCE_DIST)
                    }
                }

                developers {
                    developer {
                        id.set(POM_DEVELOPER_ID)
                        name.set(POM_DEVELOPER_NAME)
                    }
                }

                scm {
                    url.set(POM_SCM_URL)
                    connection.set(POM_SCM_CONNECTION)
                    developerConnection.set(POM_SCM_DEV_CONNECTION)
                }
            }
        }
    }

    repositories {
        maven {
            val releaseUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotUrl = uri("https://oss.sonatype.org/content/repositories/snapshots/")
            url = if (VERSION_NAME.endsWith("SNAPSHOT")) snapshotUrl else releaseUrl

            val credentials = Properties()
            credentials.load(file("./credentials.properties").inputStream())
            credentials {
                username = credentials.getProperty("sonaUsername")
                password = credentials.getProperty("sonaPassword")
            }
        }
    }
}

signing {
    sign(publishing.publications["library"])
}

