plugins {
    `android-library`
    `kotlin-android`
    `kotlin-kapt`
}

internal val Project.libs: VersionCatalog
    get() =
        project.extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    implementation(libs.findLibrary("dagger-hilt").get())
    kapt(libs.findLibrary("hilt-compiler").get())
}