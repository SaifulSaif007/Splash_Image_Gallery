plugins {
    `android-library`
    `kotlin-android`
    id("com.google.devtools.ksp")
}

internal val Project.libs: VersionCatalog
    get() =
        project.extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    implementation(libs.findLibrary("dagger-hilt").get())
    ksp(libs.findLibrary("hilt-compiler").get())
}