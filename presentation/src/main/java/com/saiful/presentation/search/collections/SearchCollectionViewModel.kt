package com.saiful.presentation.search.collections

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.saiful.core.ui.BaseViewModel
import com.saiful.core.ui.ViewEvent
import com.saiful.domain.model.CollectionItem
import com.saiful.domain.usecase.GetSearchCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchCollectionViewModel @Inject constructor(
    private val getSearchCollectionUseCase: GetSearchCollectionUseCase
) : BaseViewModel<SearchCollectionContract.Event, SearchCollectionContract.Effect>() {


    private val _collectionState: MutableStateFlow<PagingData<CollectionItem>> =
        MutableStateFlow(value = PagingData.empty())

    val collectionState: StateFlow<PagingData<CollectionItem>> get() = _collectionState

    private fun searchCollection(query: String) {
        viewModelScope.launch {
            getSearchCollectionUseCase(query)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _collectionState.value = it
                }
        }
    }

    override fun handleEvents(event: ViewEvent) {
        when (event) {
            is SearchCollectionContract.Event.SearchCollection -> {
                if (event.query.isNotEmpty()) searchCollection(event.query)
            }

        }
    }

}