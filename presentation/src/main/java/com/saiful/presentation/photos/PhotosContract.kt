package com.saiful.presentation.photos

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect
import com.saiful.domain.usecase.photoId

internal class PhotosContract {
    sealed class Event : ViewEvent {
        data class SelectPhoto(val photoId: photoId) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class NavigateDetails(val photoId: photoId) : Navigation()
        }
    }
}