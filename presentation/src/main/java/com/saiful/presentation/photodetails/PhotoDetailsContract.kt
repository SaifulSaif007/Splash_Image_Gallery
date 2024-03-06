package com.saiful.presentation.photodetails

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect
import com.saiful.domain.usecase.userName

internal class PhotoDetailsContract {
    sealed class Event : ViewEvent {
        object NavigateUp : Event()
        data class SelectProfile(val userName: userName, val profileName: String) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object NavigateUp : Navigation()
            data class ToProfile(val userName: userName, val profileName: String) : Navigation()
        }
    }

}