package com.saiful.presentation.collections

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect

internal class CollectionsContract {
    sealed class Event : ViewEvent

    sealed class Effect : ViewSideEffect
}