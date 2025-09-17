package com.saiful.presentation.search.user

import com.saiful.core.ui.ViewEvent
import com.saiful.core.ui.ViewSideEffect

class SearchUserContract {

    sealed class Event : ViewEvent {
        data class SearchUser(val query: String) : Event()
        data class SelectProfile(val userName: String, val profileName: String) : Event()
        data class SelectPhoto(val photoId: String) : Event()
    }

    sealed class Effect : ViewSideEffect {

        sealed class Navigation : Effect() {
            data class ToProfile(val userName: String, val profileName: String) : Navigation()
            data class ToPhotoDetails(val photoId: String) : Navigation()
        }
    }
}