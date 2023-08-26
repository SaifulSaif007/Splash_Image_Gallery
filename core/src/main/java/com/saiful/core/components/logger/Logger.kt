package com.saiful.core.components.logger

interface Logger {
    fun logInformation(tag: String, msg: String)
    fun logDebug(tag: String, msg: String)
    fun logWarning(tag: String, msg: String)
}
