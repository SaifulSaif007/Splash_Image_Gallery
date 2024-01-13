package com.saiful.presentation.photodetails

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect

internal class PhotoDetailsContract {
    sealed class Event : ViewEvent {
        object NavigateUp : Event()
    }

    sealed class Effect : ViewSideEffect {
        object NavigateUp : Effect()
    }

}