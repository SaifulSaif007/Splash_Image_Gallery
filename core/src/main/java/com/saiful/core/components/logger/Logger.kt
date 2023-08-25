package com.saiful.core.components.logger

interface Logger {
    fun logWithTag(tag: String, msg: String)
    fun onlyLogMsg(msg: String)
}
