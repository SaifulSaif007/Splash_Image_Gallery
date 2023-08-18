package com.saiful.splashgallery.logger

import android.app.Application
import com.saiful.splashgallery.BuildConfig
import timber.log.Timber
import javax.inject.Inject

class TimberInitializer @Inject constructor(): Initializer{
    override fun init(application: Application) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}