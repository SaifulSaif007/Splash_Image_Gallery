package com.saiful.presentation.collectionphotos

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect

internal class CollectionPhotosContract {

    sealed class Event : ViewEvent {
        data class SelectPhoto(val photoId: String) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class ToPhotoDetail(val photoId: String) : Navigation()
        }
    }
}