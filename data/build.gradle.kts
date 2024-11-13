plugins {
    `build-logic`
    `feature-common`
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

    testImplementation(project(":base_unit_test"))
    testImplementation(libs.paging.test)
    testImplementation(libs.bundles.mockito)
    testImplementation(libs.junit)
    testImplementation(libs.coroutine.test)
}