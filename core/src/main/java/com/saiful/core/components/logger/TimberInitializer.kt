package com.saiful.core.components.logger

import android.app.Application
import com.saiful.core.BuildConfig
import com.saiful.core.components.ComponentInitializer
import timber.log.Timber
import javax.inject.Inject

class TimberInitializer @Inject constructor(): ComponentInitializer {
    override fun init(application: Application) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}