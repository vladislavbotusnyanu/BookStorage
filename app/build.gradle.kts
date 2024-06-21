plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
//    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    alias(libs.plugins.compose.compiler)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android") version "2.48"
}

android {
    namespace = "com.morning_angel.book_storage"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.morning_angel.book_storage"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments.set("room.schemaLocation", "$projectDir/schemas")
//                arguments = ["room.schemaLocation", "$projectDir/schemas"]
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


dependencies {

    /** Qr Code generator */
    implementation("com.google.zxing:core:3.4.1")


    /** Compose Navigation */
    implementation("androidx.navigation:navigation-compose:2.7.7")

    /** ANDROIDX.DOCUMENT_FILE */
    implementation(libs.androidx.documentfile)

    /** ksp */
    ksp("com.google.devtools.ksp:symbol-processing:2.0.0-1.0.22")

    /** ViewModel */
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.1")

    /** NANO HTTP SERVER, HTTPD */
    implementation("org.nanohttpd:nanohttpd:2.3.1")

    /** ROOM */
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    /** Coroutines */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

    /** Hild DI */
    implementation("com.google.dagger:hilt-android:2.48")
    ksp("com.google.dagger:hilt-android-compiler:2.48")

    /** Compose livedata runtime */
    implementation("androidx.compose.runtime:runtime-livedata:1.6.8")

    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}