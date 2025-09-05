package com.saiful.data.model.search

import com.saiful.data.model.ProfileImage
import com.saiful.data.model.collection.CollectionLinks
import com.saiful.data.model.search.SearchPhoto.Photo.Links
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchCollection(
    @Json(name = "total")
    val total: Int,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "results")
    val result: List<Collection>
) {
    @JsonClass(generateAdapter = true)
    data class Collection(
        @Json(name = "id")
        val id: String,
        @Json(name = "title")
        val title: String,
        @Json(name = "description")
        val description: String?,
        @Json(name = "published_at")
        val publishedAt: String,
        @Json(name = "last_collected_at")
        val lastCollectedAt: String?,
        @Json(name = "updated_at")
        val updatedAt: String?,
        @Json(name = "featured")
        val featured: Boolean,
        @Json(name = "total_photos")
        val totalPhotos: Int,
        @Json(name = "private")
        val private: Boolean,
        @Json(name = "share_key")
        val shareKey: String?,
        @Json(name = "cover_photo")
        val coverPhoto: SearchPhoto.Photo,
        @Json(name = "user")
        val user: User,
        @Json(name = "links")
        val links: CollectionLinks
    ) {
        @JsonClass(generateAdapter = true)
        data class User(
            @Json(name = "id")
            val id: String,
            @Json(name = "username")
            val username: String,
            @Json(name = "name")
            val name: String,
            @Json(name = "portfolio_url")
            val portfolioUrl: String?,
            @Json(name = "profile_image")
            val profileImage: ProfileImage,
            @Json(name = "links")
            val links: Links
        )
    }

}
