package com.saiful.mock.mappers

import com.saiful.mock.MockApi

object UserMockApi {
    private val PROFILE = Regex("^users/[^/]+\$")
    private val PROFILE_PHOTOS = Regex("^users/[^/]+/photos\\?page=\\d+&per_page=\\d+\$")
    private val PROFILE_LIKES = Regex("^users/[^/]+/likes\\?page=\\d+&per_page=\\d+\$")
    private val PROFILE_COLLECTIONS = Regex("^users/[^/]+/collections\\?page=\\d+&per_page=\\d+\$")

    fun getMap(): Map<Regex, MockApi> = mapOf(
        PROFILE to MockApi(
            path = "users/profile"
        ),
        PROFILE_PHOTOS to MockApi(
            path = "users/photos",
            findPath = { recordedRequest ->
                val regex = """page=(\d+)""".toRegex()
                when (regex.find(recordedRequest.path.toString())?.groupValues?.get(1) ?: "") {
                    "1" -> "users/photos/one"
                    else -> "users/photos"
                }
            }
        ),
        PROFILE_LIKES to MockApi(
            path = "users/likes"
        ),
        PROFILE_COLLECTIONS to MockApi(
            path = "users/collections"
        )
    )
}