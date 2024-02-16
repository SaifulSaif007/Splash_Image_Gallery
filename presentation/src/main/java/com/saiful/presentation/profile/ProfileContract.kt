package com.saiful.presentation.profile

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect

internal class ProfileContract {
    sealed class Event : ViewEvent {
        object NavigateBack : Event()
        data class NavigateToPhotoDetails(val photoId: String) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object NavigateUp : Navigation()

            data class ToPhotoDetails(val photoId: String) : Navigation()

        }
    }
}