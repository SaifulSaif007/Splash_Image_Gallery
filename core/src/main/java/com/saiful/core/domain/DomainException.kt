package com.saiful.core.domain

import okio.IOException

data class DomainException(
    val error: String,
    override val message: String,
    val code: Int
) : IOException(message)
