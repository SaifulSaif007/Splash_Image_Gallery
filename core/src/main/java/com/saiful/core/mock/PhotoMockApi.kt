package com.saiful.core.mock

object PhotoMockApi {

    private val PHOTOS = Regex("^photos\\?page=\\d+&per_page=\\d+\$")

    fun getMap(): Map<Regex, MockApi> = mapOf(
        PHOTOS to MockApi(
            path = "photos",
            findPath = { recordedRequest ->
                val regex = """page=(\d+)""".toRegex()
                when (regex.find(recordedRequest.path.toString())?.groupValues?.get(1) ?: "") {
                    "1" -> "photos/one"
                    "2" -> "photos/two"
                    else -> "photos"
                }
            }
        )
    )
}