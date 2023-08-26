package com.saiful.splashgallery

import android.app.Application
import com.saiful.core.components.ComponentInitializer
import javax.inject.Inject

class AppInitializer @Inject constructor(
    private val componentInitializer: Set<@JvmSuppressWildcards ComponentInitializer>
) {
    fun init(application: Application) {
        componentInitializer.forEach {
            it.init(application)
        }
    }
}