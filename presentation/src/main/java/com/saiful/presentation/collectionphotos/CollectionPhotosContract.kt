package com.saiful.presentation.collectionphotos

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect

internal class CollectionPhotosContract {

    sealed class Event : ViewEvent

    sealed class Effect : ViewSideEffect
}