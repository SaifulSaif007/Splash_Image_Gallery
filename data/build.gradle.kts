plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId)
    id(libs.plugins.hilt.plugin.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.kapt.get().pluginId)
    `build-logic`
}

android {
    namespace = "com.saiful.data"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {

    implementation(project(":core"))

    implementation(libs.androidx.ktx)
    implementation(libs.bundles.retrofit)
    implementation(libs.paging.runtime)

    implementation(libs.dagger.hilt)
    kapt(libs.hilt.compiler)

    testImplementation(project(":base_unit_test"))
    testImplementation(libs.paging.test)
    testImplementation(libs.bundles.mockito)
    testImplementation(libs.junit)
    testImplementation(libs.coroutine.test)
}