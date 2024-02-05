package com.saiful.presentation.collections

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect

internal class CollectionsContract {
    sealed class Event : ViewEvent {
        data class SelectCollection(
            val collectionId: String,
            val collectionName: String,
            val collectionDesc: String,
            val totalPhotos: String,
            val collectionAuthor: String
        ) : Event()

        data class SelectProfile(val userName: String) : Event()
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

            data class ToProfile(val userName: String) : Navigation()
        }
    }
}