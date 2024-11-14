plugins {
    `build-logic`
    `feature-common`
}

android {
    namespace = "com.saiful.domain"

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
    implementation(project(":data"))

    implementation(libs.androidx.ktx)
    implementation(libs.paging.runtime)

    testImplementation(project(":base_unit_test"))
    testImplementation(libs.bundles.mockito)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.paging.test)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso)
}