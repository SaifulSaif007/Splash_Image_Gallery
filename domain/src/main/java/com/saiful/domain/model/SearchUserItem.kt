package com.saiful.domain.model

data class SearchUserItem(
    val userId: String,
    val userName: String,
    val name: String,
    val profileImage: String,
    val photos: List<Photo>
) {
    data class Photo(
        val photoId: String,
        val imageUrl: String,
    )
}
