package com.saiful.domain.model

import com.saiful.domain.usecase.photoId

data class PhotoItem(
    val photoId: photoId,
    val profileImage: String,
    val profileName: String,
    val sponsored: Boolean = false,
    val mainImage: String,
    val mainImageBlurHash: String,
    val mainImageHeight: Int,
    val mainImageWidth: Int,
    val profileUserName: String,
)
