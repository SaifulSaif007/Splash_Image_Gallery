package com.saiful.presentation.search.photos

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.saiful.core.ui.BaseViewModel
import com.saiful.core.ui.ViewEvent
import com.saiful.domain.model.PhotoItem
import com.saiful.domain.usecase.GetSearchedPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchPhotoViewModel @Inject constructor(
    private val getSearchedPhotoUseCase: GetSearchedPhotoUseCase
) : BaseViewModel<SearchPhotoContract.Event, SearchPhotoContract.Effect>() {

    private val _photoState: MutableStateFlow<PagingData<PhotoItem>> =
        MutableStateFlow(value = PagingData.empty())
    val photoState: StateFlow<PagingData<PhotoItem>> get() = _photoState


    private fun searchPhoto(query: String) {
        viewModelScope.launch {
            getSearchedPhotoUseCase(query)
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
                if (event.query.isNotEmpty()) searchPhoto(event.query)
            }

        }
    }


}