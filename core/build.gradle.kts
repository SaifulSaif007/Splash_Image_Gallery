plugins {
    `build-logic`
    `feature-common`
}

android {
    namespace = "com.saiful.core"

    defaultConfig {

        val clientID: String by project
        val apiKey = System.getenv("CLIENT_ID") ?: clientID
        buildConfigField("String", "CLIENT_ID", "\"${apiKey}\"")

        buildConfigField("boolean", "enableMock", "false")

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

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":mock"))

    implementation(libs.androidx.ktx)
    implementation(platform(libs.kotlin.bom))

    implementation(libs.hilt.navigation.compose)

    implementation(libs.bundles.retrofit)
    implementation(libs.timber)

    debugImplementation(libs.mock.webserver)
    implementation(libs.logging.interceptor)

    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso)
}