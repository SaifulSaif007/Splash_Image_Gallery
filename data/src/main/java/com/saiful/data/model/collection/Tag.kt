package com.saiful.data.model.collection

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Tag(
    @Json(name = "title")
    val title: String,
    @Json(name = "type")
    val type: String
)