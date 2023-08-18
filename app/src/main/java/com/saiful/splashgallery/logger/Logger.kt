package com.saiful.splashgallery.logger

interface Logger {
    fun logWithTag(tag: String, msg: String)
    fun onlyLogMsg(msg: String)
}
