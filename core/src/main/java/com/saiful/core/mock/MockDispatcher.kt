package com.saiful.core.mock

import okhttp3.mockwebserver.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MockDispatcher @Inject constructor(
    private val mockMaker: MockMaker
) : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return try {
            val apiEndPoint = request.path?.removePrefix("/") ?: ""
            mockMaker.findMockApi(apiEndPoint)?.let { mockApi ->
                MockResponse().setResponseCode(mockApi.responseCode).setBody(
                    ResourceUtils.getJsonString("${mockApi.findPath(request)}/${mockApi.responseCode}.json")
                ).apply {
                    if (mockApi.delay > 0) this.setBodyDelay(mockApi.delay, TimeUnit.MILLISECONDS)
                }
            } ?: throw NullPointerException("API mocker not found!")


        } catch (ex: Exception) {
            MockResponse().setResponseCode(404)
        }
    }
}