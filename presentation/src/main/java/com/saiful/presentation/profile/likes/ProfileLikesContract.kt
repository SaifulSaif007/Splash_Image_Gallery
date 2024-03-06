package com.saiful.presentation.profile.likes

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect

class ProfileLikesContract {
    sealed class Event : ViewEvent {
        data class Initialize(val username: String) : Event()
        data class SelectPhoto(val photoId: String) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class ToPhotoDetails(val photoId: String) : Navigation()
        }
    }
}