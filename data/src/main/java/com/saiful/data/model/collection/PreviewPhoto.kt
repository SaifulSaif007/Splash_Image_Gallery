package com.saiful.data.model.collection

import com.saiful.data.model.photo.Urls
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PreviewPhoto(
    @Json(name = "blur_hash")
    val blurHash: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "id")
    val id: String,
    @Json(name = "slug")
    val slug: String?,
    @Json(name = "updated_at")
    val updatedAt: String?,
    @Json(name = "urls")
    val urls: Urls?
)