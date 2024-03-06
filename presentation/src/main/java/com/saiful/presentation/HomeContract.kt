package com.saiful.presentation

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect
import com.saiful.domain.usecase.photoId
import com.saiful.domain.usecase.userName

class HomeContract {
    sealed class Event : ViewEvent {
        data class SelectPhoto(val photoId: photoId) : Event()
        data class SelectCollection(
            val collectionId: String,
            val title: String,
            val desc: String,
            val count: String,
            val author: String
        ) : Event()

        data class SelectProfile(val userName: userName, val profileName: String) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class ToPhotoDetail(val photoId: photoId) : Navigation()
            data class ToCollectionDetail(
                val collectionId: String,
                val title: String,
                val desc: String,
                val count: String,
                val author: String
            ) : Navigation()

            data class ToProfile(val userName: userName, val profileName: String) : Navigation()
        }
    }
}
