package com.saiful.presentation.photos

import com.saiful.core.domain.DomainException
import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect

internal class PhotosContract {
    sealed class Event : ViewEvent

    sealed class Effect : ViewSideEffect {
        data class GenericError(val exception: DomainException) : Effect()
    }
}