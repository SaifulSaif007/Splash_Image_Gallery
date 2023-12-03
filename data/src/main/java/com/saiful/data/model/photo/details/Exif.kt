package com.saiful.data.model.photo.details

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Exif(
    @Json(name = "make")
    val camera: String,
    @Json(name = "model")
    val model: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "exposure_time")
    val exposureTime: String,
    @Json(name = "aperture")
    val aperture: Int,
    @Json(name = "focal_length")
    val focalLength: Double,
    @Json(name = "iso")
    val iso: Int
)
