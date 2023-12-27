package com.saiful.presentation.collections

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect

internal class CollectionsContract {
    sealed class Event : ViewEvent {
        data class SelectCollection(val collectionId: String) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class ToCollectionDetails(val collectionId: String) : Navigation()
        }
    }
}