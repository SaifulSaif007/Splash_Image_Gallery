package com.saiful.mock

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.mockwebserver.MockWebServer

class MockServerManager(
    private val coroutineScope: CoroutineScope,
    private val mockDispatcher: MockDispatcher
) {
    private var mockServer: MockWebServer? = null

    fun startServer() {
        coroutineScope.launch {
            mockServer = MockWebServer()
            mockServer?.apply {
                dispatcher = mockDispatcher
            }?.start(8080)
        }.invokeOnCompletion {
            Log.d("Mock", "Mock server started")
        }
    }

}
