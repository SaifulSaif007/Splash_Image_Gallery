package com.saiful.core.domain

import okio.IOException

open class DomainException(
    open val error: String,
    override val message: String,
    open val code: Int
) : IOException(message)
