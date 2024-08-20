package com.saiful.core.mock

import okhttp3.mockwebserver.RecordedRequest

data class MockApi(
    val path: String,
    val shouldMock: Boolean = true,
    val responseCode: Int = 200,
    val delay: Long = 500L,
    val findPath: (RecordedRequest) -> String = { path }
)
