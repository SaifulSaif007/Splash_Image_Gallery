package com.saiful.presentation.collectionphotos

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect
import com.saiful.domain.usecase.userName

internal class CollectionPhotosContract {

    sealed class Event : ViewEvent {
        object NavigateBack : Event()
        data class SelectPhoto(val photoId: String) : Event()
        data class SelectProfile(val userName: userName) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object NavigateBack : Navigation()
            data class ToPhotoDetail(val photoId: String) : Navigation()
            data class ToProfile(val userName: userName) : Navigation()
        }
    }
}