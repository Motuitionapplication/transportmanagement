plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.transportmanagementfrontend"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.transportmanagementfrontend"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
           isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation(libs.logging.interceptor)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.inappmessaging.display)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
   // implementation("com.github.dangiashish:Google-Direction-Api:1.6")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.google.android.libraries.places:places:3.4.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation ("com.google.maps.android:android-maps-utils:2.2.3")
    implementation ("com.google.android.material:material:1.4.0")


    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

    implementation ("androidx.recyclerview:recyclerview:1.3.0")
    }


