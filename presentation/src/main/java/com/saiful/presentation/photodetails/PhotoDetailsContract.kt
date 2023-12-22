package com.saiful.presentation.photodetails

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect

internal class PhotoDetailsContract {
    sealed class Event : ViewEvent {}

    sealed class Effect : ViewSideEffect {}

}