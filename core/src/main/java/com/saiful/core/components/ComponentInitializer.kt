package com.saiful.core.components

import android.app.Application

interface ComponentInitializer {
    fun init(application: Application)
}