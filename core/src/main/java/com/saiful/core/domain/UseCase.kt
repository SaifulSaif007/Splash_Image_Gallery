package com.saiful.core.domain

import kotlinx.coroutines.*

abstract class UseCase<P, R>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    abstract fun execute(params: P): R

    suspend fun invoke(params: P): R {
        return withContext(dispatcher) {
            execute(params)
        }
    }

}