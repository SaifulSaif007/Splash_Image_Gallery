package com.saiful.core.domain

sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()

    data class Error(val error: Error) : Result<Nothing>()
}
