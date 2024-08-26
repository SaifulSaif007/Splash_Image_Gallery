package com.saiful.mock

class MockMaker {

    private val mockApis = mutableMapOf<Regex, MockApi>()

    private fun getMapper() = buildMap {
        putAll(mockApis)
    }

    fun putMap(map: Map<Regex, MockApi>) {
        mockApis.putAll(map)
    }

    fun findMockApi(api: String): MockApi? {
        for ((regex, mockApi) in getMapper()) {
            if (regex.containsMatchIn(api)) {
                return mockApi
            }
        }
        return null
    }
}