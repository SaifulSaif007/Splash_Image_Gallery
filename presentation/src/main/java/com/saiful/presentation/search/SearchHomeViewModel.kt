package com.saiful.presentation.search

import com.saiful.core.ui.BaseViewModel
import com.saiful.core.ui.ViewEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchHomeViewModel @Inject constructor() :
    BaseViewModel<SearchHomeContract.Event, SearchHomeContract.Effect>() {

    override fun handleEvents(event: ViewEvent) {
        when (event) {
            is SearchHomeContract.Event.SelectPhoto -> {
                setEffect { SearchHomeContract.Effect.Navigation.ToPhotoDetail(event.photoId) }
            }
        }
    }

}