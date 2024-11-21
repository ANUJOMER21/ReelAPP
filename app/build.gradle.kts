plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.Vginfotech.reelapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.Vginfotech.reelapp"
        minSdk = 24
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    implementation ("androidx.compose.material3:material3:1.0.0-beta03")
    implementation ("androidx.compose.material:material:1.2.1")

    //Compose Navigation Dependency
    implementation ("androidx.navigation:navigation-compose:2.5.2")

    //Pager
    implementation ("com.google.accompanist:accompanist-pager:0.23.1")

    //Media 3

    implementation ("androidx.media3:media3-exoplayer:1.0.0-alpha03")
    implementation ("androidx.media3:media3-ui:1.0.0-alpha03")

    // coil
    implementation ("io.coil-kt:coil-compose:2.0.0-rc01")
    implementation ("androidx.compose.foundation:foundation-layout-android:1.7.5")
    implementation ("androidx.compose.animation:animation-core-android:1.7.5")
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