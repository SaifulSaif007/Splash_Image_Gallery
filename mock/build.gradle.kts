plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.kapt.get().pluginId)
    `build-logic`
}

android {
    namespace = "com.saiful.mock"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {

    implementation(libs.mock.webserver)
    implementation(libs.logging.interceptor)

    implementation(libs.dagger.hilt)
    kapt(libs.hilt.compiler)
}