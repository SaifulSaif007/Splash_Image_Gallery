package com.saiful.core.di

import com.saiful.core.mock.*
import com.saiful.core.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MockApiModule {

    @Singleton
    @Provides
    fun provideCoroutineScope(dispatcherProvider: DispatcherProvider): CoroutineScope {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Timber.e("CoreModule -> CoroutineExceptionHandler -> ${throwable.localizedMessage}")
        }
        return CoroutineScope(SupervisorJob() + dispatcherProvider.io() + exceptionHandler)
    }

    @Singleton
    @Provides
    fun provideMockMaker(): MockMaker {
        return MockMaker().apply {
            putMap(PhotoMockApi.getMap())
        }
    }

    @Singleton
    @Provides
    fun provideMockDispatcher(mockMaker: MockMaker): MockDispatcher {
        return MockDispatcher(mockMaker)
    }

    @Singleton
    @Provides
    fun provideMockManager(
        coroutineScope: CoroutineScope,
        mockDispatcher: MockDispatcher
    ): MockServerManager {
        return MockServerManager(coroutineScope, mockDispatcher)
    }
}