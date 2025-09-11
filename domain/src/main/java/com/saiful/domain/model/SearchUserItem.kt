package com.saiful.domain.model

data class SearchUserItem(
    val userId: String,
    val userName: String,
    val name: String,
    val profileImage: String,
    val photos: List<String>
)
