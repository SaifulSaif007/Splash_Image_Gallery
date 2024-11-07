plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId)
    `build-logic`
}

android {
    namespace = "com.saiful.test.unit"

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

    implementation(project(":core"))
    implementation(libs.androidx.ktx)

    implementation(libs.coroutine.test)
    implementation(libs.junit)
    implementation(libs.junit.ext)

}