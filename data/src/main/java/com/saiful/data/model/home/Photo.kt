package com.saiful.data.model.home

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photo(
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
    val likes: Int,
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
)
