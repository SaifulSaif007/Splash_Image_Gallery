package com.saiful.core.di

import com.saiful.core.di.qualifiers.BaseUrl
import com.saiful.core.di.qualifiers.GenericErrorMessage
import com.saiful.core.utils.DefaultDispatcherProvider
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
object CoreModule {

    @Provides
    @GenericErrorMessage
    fun provideGenericMessage(): String {
        return "Something went wrong"
    }

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String {
        return "https://api.unsplash.com/"
    }

    @Provides
    @Singleton
    fun provideDispatcher(): DispatcherProvider {
        return DefaultDispatcherProvider()
    }

    @Singleton
    @Provides
    fun provideCoroutineScope(dispatcherProvider: DispatcherProvider): CoroutineScope {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Timber.e("CoreModule -> CoroutineExceptionHandler -> ${throwable.localizedMessage}")
        }
        return CoroutineScope(SupervisorJob() + dispatcherProvider.io() + exceptionHandler)
    }
}