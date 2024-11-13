plugins {
    `build-logic`
    `feature-common`
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

}