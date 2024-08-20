package com.saiful.core.mock

import com.saiful.core.components.logger.logDebug
import okhttp3.*

class MockInterceptor(
    private val baseUrl: String,
    private val mockMaker: MockMaker,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val apiEndPoint = request.url.toString().replace(baseUrl, "")
        if (mockMaker.findMockApi(apiEndPoint)?.shouldMock == true) {
            val newBaseUrl = "http://localhost:8080/"
            val newEndPointUrl = newBaseUrl + apiEndPoint
            request = request.newBuilder()
                .url(newEndPointUrl)
                .build()
        }
        logDebug("MOCK", "Mocker: request -> $request")
        return chain.proceed(request)
    }

}