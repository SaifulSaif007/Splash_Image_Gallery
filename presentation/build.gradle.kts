plugins {
    `build-logic`
    `feature-common`
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.saiful.presentation"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
            isReturnDefaultValues = true
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

    implementation(libs.hilt.navigation.compose)
    implementation(libs.json.serialization)

    implementation(libs.custombottomnavbar)

    testImplementation(project(":base_unit_test"))
    testImplementation(libs.mockK)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.paging.test)
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.compose.ui.test)
    debugImplementation(libs.compose.ui.test.manifest)

    androidTestImplementation(libs.espresso)
}