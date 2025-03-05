package com.saiful.presentation

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    data object Home : Routes()

    @Serializable
    data class PhotoDetails(val photoId: String) : Routes()

    @Serializable
    data class CollectionPhotos(
        val collectionId: String,
        val collectionTitle: String,
        val collectionDescription: String,
        val collectionPhotoCount: String,
        val collectionAuthor: String
    ) : Routes()

    @Serializable
    data class Profile(val userName: String, val userProfileName: String) : Routes()

}