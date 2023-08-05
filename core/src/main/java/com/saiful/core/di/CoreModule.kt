package com.saiful.core.di

import com.saiful.core.di.qualifiers.BaseUrl
import com.saiful.core.di.qualifiers.GenericErrorMessage
import dagger.Provides

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