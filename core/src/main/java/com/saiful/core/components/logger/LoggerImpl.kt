package com.saiful.core.components.logger

import timber.log.Timber

class LoggerImpl : Logger {
    override fun logWithTag(tag: String, msg: String) {
        Timber.tag(tag).d(msg)
    }

    override fun onlyLogMsg(msg: String) {
        Timber.d(msg)
    }
}