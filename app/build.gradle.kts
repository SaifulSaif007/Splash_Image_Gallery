@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.com.android.app)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
}

android {
    namespace = "com.saiful.splashgallery"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.saiful.splashgallery"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility =  JavaVersion.VERSION_17
        targetCompatibility =  JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

dependencies {

    implementation(libs.androidx.ktx)
    implementation (platform(libs.kotlin.bom))
    implementation (libs.androidx.lifecycle)
    implementation (libs.androidx.activity.compose)

    implementation (platform(libs.androidx.compose.bom))
    implementation (libs.compose.ui)
    implementation (libs.compose.ui.graphics)
    implementation (libs.compose.ui.tooling)
    implementation (libs.compose.material3)

    implementation(libs.dagger.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.timber)

    testImplementation (libs.junit)
    androidTestImplementation (libs.junit.ext)
    androidTestImplementation (libs.espresso)
    androidTestImplementation (platform(libs.compose.test.boom))
    androidTestImplementation (libs.compose.ui.test)
    debugImplementation (libs.compose.test.ui.tooling)
    debugImplementation (libs.compose.ui.test.manifest)
}