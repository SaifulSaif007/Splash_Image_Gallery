package com.saiful.splashgallery

import android.app.Application
import com.saiful.splashgallery.logger.AppInitializer
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

@HiltAndroidApp
class SplashApplication : Application() {

    @Inject
    lateinit var appInitializer: AppInitializer

    override fun onCreate() {
        super.onCreate()

        appInitializer.init(this)
    }
}