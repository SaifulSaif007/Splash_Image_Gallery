package com.saiful.presentation.photos

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.saiful.core.ui.BaseViewModel
import com.saiful.core.ui.ViewEvent
import com.saiful.domain.model.PhotoItem
import com.saiful.domain.usecase.GetPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PhotosViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase
) : BaseViewModel<PhotosContract.Event, PhotosContract.Effect>() {

    private val _photoState: MutableStateFlow<PagingData<PhotoItem>> =
        MutableStateFlow(value = PagingData.empty())
    val photoState: StateFlow<PagingData<PhotoItem>> get() = _photoState

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            getPhotosUseCase(Unit)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _photoState.value = it
                }
        }
    }


    override fun handleEvents(event: ViewEvent) {
        when(event){
            is PhotosContract.Event.SelectPhoto -> {
                setEffect {
                    PhotosContract.Effect.Navigation.NavigateDetails
                }
            }
        }
    }

}