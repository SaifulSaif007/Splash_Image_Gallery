package com.saiful.presentation.photos

import com.saiful.core.domain.DomainException
import com.saiful.core.ui.*
import com.saiful.domain.model.HomeItem

internal class PhotosContract {
    sealed class Event : ViewEvent

    data class State(
        val loading: Boolean = false,
        val photos: List<HomeItem> = emptyList()
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data class GenericError(val exception: DomainException) : Effect()
    }
}