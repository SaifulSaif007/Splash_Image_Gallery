package com.saiful.core.mock

import com.saiful.core.components.logger.logDebug
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
            logDebug(msg = "Mock server started")
        }
    }

}
