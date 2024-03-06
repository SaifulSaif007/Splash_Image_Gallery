package com.saiful.presentation.profile

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect

internal class ProfileContract {
    sealed class Event : ViewEvent {
        object NavigateBack : Event()
        data class NavigateToPhotoDetails(val photoId: String) : Event()

        data class NavigateToCollection(
            val collectionId: String,
            val title: String,
            val desc: String,
            val count: String,
            val author: String
        ) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object NavigateUp : Navigation()

            data class ToPhotoDetails(val photoId: String) : Navigation()

            data class ToCollectionDetail(
                val collectionId: String,
                val title: String,
                val desc: String,
                val count: String,
                val author: String
            ) : Navigation()
        }
    }
}