package com.saiful.core.data

import com.saiful.core.domain.DomainException
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection

class ResponseInterceptor(
    private val moshi: Moshi,
    private val genericErrorMsg: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        return chain.proceed(chain.request()).apply {
            if (!this.isSuccessful) {
                try {
                    when (this.code) {
                        HttpURLConnection.HTTP_UNAUTHORIZED -> {
                            this.body?.source()?.let { source ->
                                moshi.adapter(ApiErrorResponse::class.java).fromJson(source)
                                    ?.let { apiErrorResponse ->
                                        throw DomainException(
                                            error = if (apiErrorResponse.errors.isNotEmpty()) apiErrorResponse.errors[0] else genericErrorMsg,
                                            message = "unauthorized",
                                            code = this.code
                                        )
                                    }
                            }
                        }

                        else -> {
                            throw DomainException(
                                error = "unknown",
                                message = genericErrorMsg,
                                code = this.code
                            )
                        }
                    }
                } catch (ex: Exception) {
                    throw DomainException(
                        error = "unknown",
                        message = genericErrorMsg,
                        code = this.code
                    )
                }
            }
        }
    }
}