// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id(libs.plugins.com.android.app.get().pluginId) apply false
    id(libs.plugins.com.android.library.get().pluginId) apply false
    id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId) apply false
    id(libs.plugins.hilt.plugin.get().pluginId)  apply false
    id(libs.plugins.org.jetbrains.kotlin.kapt.get().pluginId) apply false
    id(libs.plugins.org.jetbrains.kotlin.jvm.get().pluginId) apply false
    alias(libs.plugins.gms.google.service) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}