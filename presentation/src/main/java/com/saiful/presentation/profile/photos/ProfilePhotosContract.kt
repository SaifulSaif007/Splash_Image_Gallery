package com.saiful.presentation.profile.photos

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect
import com.saiful.domain.usecase.photoId
import com.saiful.domain.usecase.userName

class ProfilePhotosContract {
    sealed class Event : ViewEvent {
        data class Initialize(val userName: userName) : Event()
        data class SelectPhoto(val photoId: photoId) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class ToPhotoDetails(val photoId: photoId) : Navigation()
        }
    }
}