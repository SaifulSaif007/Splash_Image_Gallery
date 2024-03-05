package com.saiful.presentation.profile.collection

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect

internal class ProfileCollectionContract {
    sealed class Event : ViewEvent {
        data class Initialize(val username: String) : Event()

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
            data class ToCollectionDetails(
                val collectionId: String,
                val collectionName: String,
                val collectionDesc: String,
                val totalPhotos: String,
                val collectionAuthor: String
            ) : Navigation()

        }
    }
}