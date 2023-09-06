package com.saiful.core.components.logger

import timber.log.Timber

internal class LoggerImpl : Logger {
    override fun logInformation(tag: String, msg: String) {
        Timber.tag(tag).i(msg)
    }

    override fun logDebug(tag: String, msg: String) {
        Timber.tag(tag).d(msg)
    }

    override fun logWarning(tag: String, msg: String) {
        Timber.tag(tag).w(msg)
    }
}