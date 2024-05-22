@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
}

android {
    namespace = "com.saiful.presentation"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.5"
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {

    implementation(project(":core"))
    implementation(project(":domain"))

    implementation(libs.androidx.ktx)
    implementation(libs.coil.image.loader)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.compose.navigation)
    implementation(libs.viewModel.lifecycle.compose)
    implementation(libs.bundles.lifecycle)
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)

    implementation(libs.dagger.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    testImplementation(project(":base_unit_test"))
    testImplementation(libs.bundles.mockito)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.paging.test)
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.compose.ui.test)
    debugImplementation(libs.compose.ui.test.manifest)

    androidTestImplementation(libs.espresso)
}