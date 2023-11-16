package com.saiful.core.domain

import kotlinx.coroutines.*

abstract class UseCase<P, R>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    abstract suspend fun execute(params: P): R

    suspend operator fun invoke(params: P): R {
        return withContext(dispatcher) {
            execute(params)
        }
    }

}