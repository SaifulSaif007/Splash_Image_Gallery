package com.saiful.data.model.search

import com.saiful.data.model.ProfileImage
import com.saiful.data.model.photo.Photo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SearchUser(
    @Json(name = "total")
    val total: Int,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "results")
    val result: List<User>
) {
    @JsonClass(generateAdapter = true)
    data class User(
        @Json(name = "id")
        val id: String,
        @Json(name = "username")
        val username: String,
        @Json(name = "name")
        val name: String,
        @Json(name = "first_name")
        val firstName: String,
        @Json(name = "last_name")
        val lastName: String?,
        @Json(name = "instagram_username")
        val instagramUsername: String?,
        @Json(name = "twitter_username")
        val twitterUsername: String?,
        @Json(name = "portfolio_url")
        val portfolioUrl: String?,
        @Json(name = "total_collections")
        val totalCollections: Int,
        @Json(name = "total_likes")
        val totalLikes: Int,
        @Json(name = "total_photos")
        val totalPhotos: Int,
        @Json(name = "profile_image")
        val profileImage: ProfileImage,
        @Json(name = "links")
        val links: Links,
        @Json(name = "Photo")
        var photo: List<Photo> = emptyList()
    )

    @JsonClass(generateAdapter = true)
    data class Links(
        @Json(name = "html")
        val html: String,
        @Json(name = "likes")
        val likes: String,
        @Json(name = "photos")
        val photos: String,
        @Json(name = "self")
        val self: String
    )
}