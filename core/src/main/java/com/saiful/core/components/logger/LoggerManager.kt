package com.saiful.core.components.logger

import androidx.activity.ComponentActivity

fun ComponentActivity.log(msg: String) =
    LoggerImpl().logWithTag(tag = this.javaClass.simpleName, msg = msg)