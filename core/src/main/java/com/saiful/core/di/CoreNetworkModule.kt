package com.saiful.core.di

import com.saiful.core.BuildConfig
import com.saiful.core.data.RequestInterceptor
import com.saiful.core.data.ResponseInterceptor
import com.saiful.core.di.qualifiers.BaseUrl
import com.saiful.core.di.qualifiers.GenericErrorMessage
import com.saiful.mock.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object CoreNetworkModule {

    private val loggingInterceptor: HttpLoggingInterceptor = if (BuildConfig.DEBUG) {
        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun requestInterceptor(): RequestInterceptor {
        return RequestInterceptor()
    }

    @Provides
    fun responseInterceptor(
        moshi: Moshi,
        @GenericErrorMessage genericErrorMessage: String
    ): ResponseInterceptor {
        return ResponseInterceptor(
            moshi = moshi,
            genericErrorMsg = genericErrorMessage
        )
    }

    @Provides
    fun provideOKHttpClient(
        requestInterceptor: RequestInterceptor,
        responseInterceptor: ResponseInterceptor,
        @BaseUrl baseUrl: String,
        mockMaker: MockMaker,
        mockServerManager: MockServerManager
    ): OkHttpClient {

        mockServerManager.startServer()

        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(requestInterceptor)
            .addInterceptor(responseInterceptor)
            .addInterceptor(MockInterceptor(baseUrl, mockMaker))
            .addNetworkInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient,
        @BaseUrl baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }
}