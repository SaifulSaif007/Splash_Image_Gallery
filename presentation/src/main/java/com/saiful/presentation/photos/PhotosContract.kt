package com.saiful.presentation.photos

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect

internal class PhotosContract {
    sealed class Event : ViewEvent {
        object SelectPhoto : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object NavigateDetails : Navigation()
        }
    }
}