package com.saiful.data.model.home

internal data class Photo(
    val altDescription: String,
    val blurHash: String,
    val breadcrumbs: List<Any>,
    val color: String,
    val createdAt: String,
    val currentUserCollections: List<Any>,
    val description: String?,
    val height: Int,
    val id: String,
    val likedByUser: Boolean,
    val likes: Int,
    val links: Links,
    val promotedAt: String?,
    val slug: String,
    val sponsorship: Sponsorship?,
    val updatedAt: String,
    val urls: Urls,
    val user: User,
    val width: Int
)
