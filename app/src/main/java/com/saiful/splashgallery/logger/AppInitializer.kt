package com.saiful.splashgallery.logger

import android.app.Application
import javax.inject.Inject

class AppInitializer @Inject constructor(
    private val initializer: Set<@JvmSuppressWildcards Initializer>
) {
    fun init(application: Application) {
        initializer.forEach {
            it.init(application)
        }
    }
}