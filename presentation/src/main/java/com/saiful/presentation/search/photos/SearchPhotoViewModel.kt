package com.saiful.presentation.search.photos

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.saiful.core.ui.BaseViewModel
import com.saiful.core.ui.ViewEvent
import com.saiful.domain.model.PhotoItem
import com.saiful.domain.usecase.GetSearchPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchPhotoViewModel @Inject constructor(
    private val getSearchPhotoUseCase: GetSearchPhotoUseCase
) : BaseViewModel<SearchPhotoContract.Event, SearchPhotoContract.Effect>() {

    private val _photoState: MutableStateFlow<PagingData<PhotoItem>> =
        MutableStateFlow(value = PagingData.empty())
    val photoState: StateFlow<PagingData<PhotoItem>> get() = _photoState

    var currentQuery = MutableStateFlow<String?>(null)

    private fun searchPhoto(query: String) {
        _photoState.value = PagingData.empty()
        viewModelScope.launch {
            getSearchPhotoUseCase(query)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _photoState.value = it
                }
        }
    }

    override fun handleEvents(event: ViewEvent) {
        when (event) {
            is SearchPhotoContract.Event.SearchPhoto -> {
                if (event.query.isNotEmpty() && event.query != currentQuery.value) {
                    currentQuery.value = event.query
                    searchPhoto(event.query)
                }
            }

            is SearchPhotoContract.Event.SelectPhoto -> {
                setEffect { SearchPhotoContract.Effect.Navigation.ToPhotoDetails(event.photoId) }
            }

            is SearchPhotoContract.Event.SelectProfile -> {
                setEffect {
                    SearchPhotoContract.Effect.Navigation.ToProfile(
                        event.userName,
                        event.profileName
                    )
                }
            }

        }
    }


}