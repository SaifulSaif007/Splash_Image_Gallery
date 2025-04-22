package com.saiful.core.data

import com.saiful.core.domain.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NoInternetInterceptor(
    private val internetAvailabilityRepository: InternetAvailabilityRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!internetAvailabilityRepository.isConnected.value) {
            throw NoInternetException()
        }
        return chain.proceed(chain.request())
    }

}