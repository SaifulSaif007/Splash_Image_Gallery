package com.saiful.presentation.profile

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect

internal class ProfileContract {
    sealed class Event : ViewEvent {
        object navigateBack : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object navigateUp : Navigation()
        }
    }
}