package com.saiful.domain.model

data class HomeItem(
    val profileImage: String,
    val profileName: String,
    val sponsored: Boolean = false,
    val mainImage: String
)
