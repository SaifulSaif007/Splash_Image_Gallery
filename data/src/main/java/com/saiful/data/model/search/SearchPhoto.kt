package com.saiful.data.model.search

import com.saiful.data.model.ProfileImage
import com.saiful.data.model.photo.Urls
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchPhoto(
    @Json(name = "total")
    val total: Int,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "results")
    val result: List<Photo>
) {
    @JsonClass(generateAdapter = true)
    data class Photo(
        @Json(name = "id")
        val id: String,
        @Json(name = "created_at")
        val createdAt: String?,
        @Json(name = "height")
        val height: Int,
        @Json(name = "width")
        val width: Int,
        @Json(name = "color")
        val color: String?,
        @Json(name = "blur_hash")
        val blurHash: String?,
        @Json(name = "likes")
        val likes: Long,
        @Json(name = "liked_by_user")
        val likedByUser: Boolean,
        @Json(name = "description")
        val description: String?,
        @Json(name = "user")
        val user: User,
        @Json(name = "urls")
        val urls: Urls,
        @Json(name = "links")
        val links: ContentLink?,
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
            @Json(name = "profile_image")
            val profileImage: ProfileImage,
            @Json(name = "links")
            val links: Links
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

        @JsonClass(generateAdapter = true)
        data class ContentLink(
            @Json(name = "download")
            val download: String,
            @Json(name = "html")
            val html: String,
            @Json(name = "self")
            val self: String
        )
    }
}
