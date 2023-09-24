package com.saiful.presentation.model

import androidx.compose.runtime.Stable

@Stable
internal data class HomeItem(
    val profileImage: String,
    val profileName: String,
    val sponsored: Boolean = false,
    val mainImage: String
)
