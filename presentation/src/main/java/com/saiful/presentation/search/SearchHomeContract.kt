package com.saiful.presentation.search

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect
import com.saiful.domain.usecase.photoId
import com.saiful.domain.usecase.userName

class SearchHomeContract {
    sealed class Event : ViewEvent {
        data class SelectPhoto(val photoId: photoId) : Event()
        data class SelectProfile(val userName: userName, val profileName: String) : Event()
        data class SelectCollection(
            val collectionId: String,
            val collectionName: String,
            val collectionDesc: String,
            val totalPhotos: String,
            val collectionAuthor: String
        ) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class ToPhotoDetail(val photoId: photoId) : Navigation()
            data class ToProfile(val userName: userName, val profileName: String) : Navigation()
            data class ToCollectionDetail(
                val collectionId: String,
                val collectionName: String,
                val collectionDesc: String,
                val totalPhotos: String,
                val collectionAuthor: String
            ) : Navigation()
        }
    }
}