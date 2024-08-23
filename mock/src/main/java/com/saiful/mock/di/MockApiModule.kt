package com.saiful.mock.di

import com.saiful.mock.*
import com.saiful.mock.mappers.PhotoMockApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MockApiModule {

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