package com.saiful.domain.model

data class ProfileInfo(
    val profileName: String,
    val location: String,
    val bio: String,
    val profileImage: String,
    val photos: String,
    val likes: String,
    val collection: String,
    val visibleTabs: List<String>
)
