package com.saiful.data.model.photo.details

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageLocation(
    @Json(name = "name")
    val name: String?,
    @Json(name = "city")
    val city: String?,
    @Json(name = "country")
    val country: String?
)
