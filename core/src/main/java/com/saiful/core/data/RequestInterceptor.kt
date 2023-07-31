package com.saiful.core.data

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RequestInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val CLIENT_ID = "anything"

        val original = chain.request()
        val builder =
            original.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Version", "v1")
                .addHeader("Authorization", "Client-ID $CLIENT_ID")
                .build()

        return chain.proceed(builder)
    }
}