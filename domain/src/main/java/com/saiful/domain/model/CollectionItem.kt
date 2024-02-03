package com.saiful.domain.model

data class CollectionItem(
    val collectionId: String,
    val profileImage: String,
    val profileName: String,
    val mainImage: String,
    val mainImageBlurHash: String,
    val mainImageHeight: Int,
    val mainImageWidth: Int,
    val title: String,
    val description: String,
    val totalPhoto: Int,
    val profileUserName: String,
)
