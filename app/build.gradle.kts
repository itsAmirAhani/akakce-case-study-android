plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.akakcecase"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.akakcecase"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    //material3
    implementation("androidx.compose.material3:material3:1.2.1")
    // Jetpack Compose
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui:1.5.2")
    implementation("androidx.compose.material:material:1.5.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.2")

    // Navigation for Compose
    implementation("androidx.navigation:navigation-compose:2.7.5")

    // Coil for image loading
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Retrofit + Gson
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Lifecycle ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    // Compose Foundation for LazyVerticalGrid
    implementation("androidx.compose.foundation:foundation:1.5.2")

    debugImplementation("androidx.compose.ui:ui-tooling:1.5.2")
}
