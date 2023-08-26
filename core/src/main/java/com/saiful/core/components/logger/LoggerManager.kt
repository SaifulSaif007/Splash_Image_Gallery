package com.saiful.core.components.logger

const val TAG = "TAG"
fun log(tag: String = TAG, msg: String) = LoggerImpl().logDebug(tag, msg)

fun logInfo(tag: String = TAG, msg: String) = LoggerImpl().logInformation(tag, msg)

fun logWarning(tag: String = TAG, msg: String) = LoggerImpl().logWarning(tag, msg)
