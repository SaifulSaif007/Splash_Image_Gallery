package com.saiful.core.domain

data class NoInternetException(
    override val error: String = "No internet",
    override val message: String = "No internet, check your internet connection",
    override val code: Int = 0
) : DomainException(error, message, code)
