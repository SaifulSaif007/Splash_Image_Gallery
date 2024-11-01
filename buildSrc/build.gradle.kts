plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(gradleApi())
    implementation(libs.build.gradle)
    implementation(libs.build.gradle.api)
    implementation(libs.kotlin.gradle)
}