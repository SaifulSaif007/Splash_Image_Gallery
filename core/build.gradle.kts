plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.kapt.get().pluginId)
    `build-logic`
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
    implementation(libs.dagger.hilt)
    kapt(libs.hilt.compiler)

    implementation(libs.bundles.retrofit)
    implementation(libs.timber)

    debugImplementation(libs.mock.webserver)
    implementation(libs.logging.interceptor)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso)
}