package com.saiful.domain.model

data class PhotoItem(
    val profileImage: String,
    val profileName: String,
    val sponsored: Boolean = false,
    val mainImage: String,
    val mainImageBlurHash: String,
    val mainImageHeight: Int,
    val mainImageWidth: Int,
)
