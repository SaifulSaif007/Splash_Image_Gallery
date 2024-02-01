package com.saiful.presentation.collectionphotos

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.saiful.core.ui.BaseViewModel
import com.saiful.core.ui.ViewEvent
import com.saiful.domain.model.PhotoItem
import com.saiful.domain.usecase.GetCollectionPhotoUseCase
import com.saiful.domain.usecase.collectionId
import com.saiful.presentation.utils.Constants.COLLECTION_AUTHOR
import com.saiful.presentation.utils.Constants.COLLECTION_DESCRIPTION
import com.saiful.presentation.utils.Constants.COLLECTION_ID
import com.saiful.presentation.utils.Constants.COLLECTION_PHOTO_COUNT
import com.saiful.presentation.utils.Constants.COLLECTION_TITLE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CollectionPhotosViewModel @Inject constructor(
    private val collectionPhotoUseCase: GetCollectionPhotoUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<CollectionPhotosContract.Event, CollectionPhotosContract.Effect>() {

    private val _photoState: MutableStateFlow<PagingData<PhotoItem>> =
        MutableStateFlow(value = PagingData.empty())
    val photoState: StateFlow<PagingData<PhotoItem>> get() = _photoState

    private val collectionId: String = checkNotNull(savedStateHandle[COLLECTION_ID])
    val collectionName: String = checkNotNull(savedStateHandle[COLLECTION_TITLE])
    val collectionDesc: String = checkNotNull(savedStateHandle[COLLECTION_DESCRIPTION])
    val collectionPhotoCount: String = checkNotNull(savedStateHandle[COLLECTION_PHOTO_COUNT])
    val collectionAuthor: String = checkNotNull(savedStateHandle[COLLECTION_AUTHOR])

    init {
        getCollectionPhotos(collectionId)
    }

    private fun getCollectionPhotos(collectionId: collectionId) {
        viewModelScope.launch {
            collectionPhotoUseCase(collectionId)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _photoState.value = it
                }
        }
    }

    override fun handleEvents(event: ViewEvent) {
        when (event) {
            is CollectionPhotosContract.Event.SelectPhoto -> {
                setEffect { CollectionPhotosContract.Effect.Navigation.ToPhotoDetail(event.photoId) }
            }

            is CollectionPhotosContract.Event.NavigateBack -> {
                setEffect { CollectionPhotosContract.Effect.Navigation.NavigateBack }
            }

            is CollectionPhotosContract.Event.SelectProfile -> {
                setEffect { CollectionPhotosContract.Effect.Navigation.ToProfile(event.userName) }
            }
        }

    }
}