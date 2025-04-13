package com.saiful.data.model.profile

import com.saiful.data.model.*
import com.saiful.data.model.collection.PreviewPhoto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Profile(
    @Json(name = "accepted_tos")
    val acceptedTos: Boolean,
    @Json(name = "bio")
    val bio: String?,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "for_hire")
    val forHire: Boolean,
    @Json(name = "id")
    val id: String,
    @Json(name = "instagram_username")
    val instagramUsername: String?,
    @Json(name = "last_name")
    val lastName: String?,
    @Json(name = "links")
    val links: Links,
    @Json(name = "location")
    val location: String?,
    @Json(name = "name")
    val name: String,
    @Json(name = "portfolio_url")
    val portfolioUrl: String?,
    @Json(name = "profile_image")
    val profileImage: ProfileImage,
    @Json(name = "social")
    val social: Social?,
    @Json(name = "total_collections")
    val totalCollections: Long,
    @Json(name = "total_likes")
    val totalLikes: Long,
    @Json(name = "total_photos")
    val totalPhotos: Long,
    @Json(name = "twitter_username")
    val twitterUsername: String?,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "username")
    val username: String,
    @Json(name = "total_promoted_photos")
    val totalPromotedPhotos: Long?,
    @Json(name = "followed_by_user")
    val followedByUser: Boolean = false,
    @Json(name = "photos")
    val previewPhoto: List<PreviewPhoto>,
    @Json(name = "downloads")
    val downloads: Long,
)

