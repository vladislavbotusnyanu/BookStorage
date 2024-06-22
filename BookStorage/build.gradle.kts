import java.net.URI

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.compose.compiler) apply false

    id("com.android.library") version "8.0.0" apply false
    id("com.google.devtools.ksp") version "2.0.0-1.0.21" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false

}

buildscript {
    repositories {
        mavenCentral()

//        /** EPUB Reader */
//        maven("https://github.com/psiegman/mvn-repo/raw/master/releases")
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
    }
}
