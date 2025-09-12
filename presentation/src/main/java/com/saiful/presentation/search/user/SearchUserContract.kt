package com.saiful.presentation.search.user

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect

class SearchUserContract {

    sealed class Event : ViewEvent {
        data class SearchUser(val query: String) : Event()
    }

    sealed class Effect : ViewSideEffect {

    }
}