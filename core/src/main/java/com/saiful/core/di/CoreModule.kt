package com.saiful.core.di

import com.saiful.core.di.qualifiers.BaseUrl
import com.saiful.core.di.qualifiers.GenericErrorMessage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent

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
}