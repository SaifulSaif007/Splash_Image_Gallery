package com.saiful.presentation.collections

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.saiful.core.ui.BaseViewModel
import com.saiful.core.ui.ViewEvent
import com.saiful.domain.model.CollectionItem
import com.saiful.domain.usecase.GetCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CollectionsViewModel @Inject constructor(
    private val getCollectionUseCase: GetCollectionUseCase
) : BaseViewModel<CollectionsContract.Event, CollectionsContract.Effect>() {

    private val _collectionState: MutableStateFlow<PagingData<CollectionItem>> =
        MutableStateFlow(value = PagingData.empty())

    val collectionState: StateFlow<PagingData<CollectionItem>> get() = _collectionState

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            getCollectionUseCase(Unit)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _collectionState.value = it
                }
        }
    }

    override fun handleEvents(event: ViewEvent) {
        when (event) {
            is CollectionsContract.Event.SelectCollection -> {
                setEffect {
                    CollectionsContract.Effect.Navigation.ToCollectionDetails(
                        collectionId = event.collectionId,
                        collectionName = event.collectionName,
                        collectionDesc = event.collectionDesc,
                        totalPhotos = event.totalPhotos,
                        collectionAuthor = event.collectionAuthor
                    )
                }
            }

            is CollectionsContract.Event.SelectProfile -> {
                setEffect {
                    CollectionsContract.Effect.Navigation.ToProfile(
                        event.userName,
                        event.profileName
                    )
                }
            }
        }
    }
}