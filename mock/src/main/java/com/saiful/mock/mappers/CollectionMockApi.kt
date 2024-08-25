package com.saiful.mock.mappers

import com.saiful.mock.MockApi

object CollectionMockApi {
    private val COLLECTIONS = Regex("^collections\\?page=\\d+&per_page=\\d+\$")
    private val COLLECTION_PHOTOS = Regex("^collections/[^/]+/photos\\?page=\\d+&per_page=\\d+\$")

    fun getMap(): Map<Regex, MockApi> = mapOf(
        COLLECTIONS to MockApi(
            path = "collections/list",
            findPath = { recordedRequest ->
                val regex = """page=(\d+)""".toRegex()
                when (regex.find(recordedRequest.path.toString())?.groupValues?.get(1) ?: "") {
                    "1" -> "collections/list/one"
                    "2" -> "collections/list/two"
                    else -> "collections/list"
                }
            }
        ),
        COLLECTION_PHOTOS to MockApi(
            path = "collection/photos",
            findPath = { recordedRequest ->
                val regex = """page=(\d+)""".toRegex()
                when (regex.find(recordedRequest.path.toString())?.groupValues?.get(1) ?: "") {
                    "1" -> "collections/photos/one"
                    else -> "collections/photos"
                }
            }
        )

    )
}