package com.saiful.presentation.collectionphotos

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.saiful.core.ui.BaseViewModel
import com.saiful.core.ui.ViewEvent
import com.saiful.domain.model.PhotoItem
import com.saiful.domain.usecase.GetCollectionPhotoUseCase
import com.saiful.domain.usecase.collectionId
import com.saiful.presentation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

    private val collection: Routes.CollectionPhotos = savedStateHandle.toRoute()
    private val collectionId: String = collection.collectionId
    val collectionName: String = collection.collectionTitle
    val collectionDesc: String = collection.collectionDescription
    val collectionPhotoCount: String = collection.collectionPhotoCount
    val collectionAuthor: String = collection.collectionAuthor

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
                setEffect {
                    CollectionPhotosContract.Effect.Navigation.ToProfile(
                        event.userName,
                        event.profileName
                    )
                }
            }
        }

    }
}