package com.saiful.data.model.photo.details

import com.saiful.data.model.User
import com.saiful.data.model.photo.ContentLink
import com.saiful.data.model.photo.Sponsorship
import com.saiful.data.model.photo.Urls
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoDetails(
    @Json(name = "id")
    val id: String,
    @Json(name = "alt_description")
    val altDescription: String?,
    @Json(name = "blur_hash")
    val blurHash: String?,
    @Json(name = "color")
    val color: String?,
    @Json(name = "height")
    val height: Int,
    @Json(name = "width")
    val width: Int,
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "current_user_collections")
    val currentUserCollections: List<Any>,
    @Json(name = "description")
    val description: String?,
    @Json(name = "liked_by_user")
    val likedByUser: Boolean,
    @Json(name = "likes")
    val likes: Long,
    @Json(name = "links")
    val links: ContentLink?,
    @Json(name = "promoted_at")
    val promotedAt: String?,
    @Json(name = "slug")
    val slug: String,
    @Json(name = "sponsorship")
    val sponsorship: Sponsorship?,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "urls")
    val urls: Urls,
    @Json(name = "user")
    val user: User,
    @Json(name = "exif")
    val exif: Exif,
    @Json(name = "location")
    val location: ImageLocation?,
    @Json(name = "tags")
    val tags: List<Tag>,
    @Json(name = "views")
    val views: Long,
    @Json(name = "downloads")
    val downloads: Long
)