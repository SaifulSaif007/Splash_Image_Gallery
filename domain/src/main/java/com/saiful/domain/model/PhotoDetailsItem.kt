package com.saiful.domain.model

data class PhotoDetailsItem(
    val profileImage: String,
    val profileName: String,
    val mainImage: String,
    val thumbnailImage: String,
    val camera: String,
    val focalLength: String,
    val aperture: String,
    val shutterSpeed: String,
    val iso: String,
    val dimensions: String,
    val views: String,
    val downloads: String,
    val likes: String,
    val tags: List<String>,
    val userName: String
)
