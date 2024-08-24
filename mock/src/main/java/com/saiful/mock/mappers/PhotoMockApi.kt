package com.saiful.mock.mappers

import com.saiful.mock.MockApi

object PhotoMockApi {

    private val PHOTOS = Regex("^photos\\?page=\\d+&per_page=\\d+\$")
    private val PHOTO_DETAILS = Regex("^photos/[^/]+\$")

    fun getMap(): Map<Regex, MockApi> = mapOf(
        PHOTOS to MockApi(
            path = "photos",
            findPath = { recordedRequest ->
                val regex = """page=(\d+)""".toRegex()
                when (regex.find(recordedRequest.path.toString())?.groupValues?.get(1) ?: "") {
                    "1" -> "photos/list/one"
                    "2" -> "photos/list/two"
                    else -> "photos/list"
                }
            }
        ),
        PHOTO_DETAILS to MockApi(
            path = "photos/details"
        )
    )
}