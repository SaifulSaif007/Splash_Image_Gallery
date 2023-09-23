package com.saiful.core.components.logger

const val TAG = "TAG"

/**
 *  Logger functions for the app.
 *  Use LogDebug for debug, LogError for error
 *  Also use logInfo and LogWarning for info and warning
 */

fun logDebug(tag: String = TAG, msg: String) = LoggerImpl().logDebug(tag, msg)

fun logError(tag: String = TAG, msg: String) = LoggerImpl().logError(tag, msg)

fun logInfo(tag: String = TAG, msg: String) = LoggerImpl().logInformation(tag, msg)

fun logWarning(tag: String = TAG, msg: String) = LoggerImpl().logWarning(tag, msg)
