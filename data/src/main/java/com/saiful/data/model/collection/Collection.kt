package com.saiful.data.model.collection

import com.saiful.data.model.User
import com.saiful.data.model.home.Photo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class Collection(
    @Json(name = "id")
    val id: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "published_at")
    val publishedAt: String,
    @Json(name = "last_collected_at")
    val lastCollectedAt: String,
    @Json(name = "featured")
    val featured: Boolean,
    @Json(name = "total_photos")
    val totalPhotos: Int,
    @Json(name = "private")
    val private: Boolean,
    @Json(name = "share_key")
    val shareKey: String,
    @Json(name = "tags")
    val tags: List<Tag>,
    @Json(name = "user")
    val user: User,
    @Json(name = "cover_photo")
    val coverPhoto: Photo,
    @Json(name = "preview_photos")
    val previewPhotos: List<PreviewPhoto>,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "links")
    val links: CollectionLinks
)