package com.saiful.core.di.qualifiers

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