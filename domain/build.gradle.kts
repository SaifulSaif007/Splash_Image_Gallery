@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId)
    alias(libs.plugins.hilt.plugin)
    id(libs.plugins.org.jetbrains.kotlin.kapt.get().pluginId)
}

android {
    namespace = "com.saiful.domain"
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
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_19.toString()
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {

    implementation(project(":core"))
    implementation(project(":data"))

    implementation(libs.dagger.hilt)
    kapt(libs.hilt.compiler)
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