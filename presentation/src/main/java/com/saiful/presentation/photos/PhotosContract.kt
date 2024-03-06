package com.saiful.presentation.photos

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect
import com.saiful.domain.usecase.photoId
import com.saiful.domain.usecase.userName

internal class PhotosContract {
    sealed class Event : ViewEvent {
        data class SelectPhoto(val photoId: photoId) : Event()
        data class SelectProfile(val userName: userName, val profileName: String) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class ToPhotoDetails(val photoId: photoId) : Navigation()
            data class ToProfile(val userName: userName, val profileName: String) : Navigation()
        }
    }
}