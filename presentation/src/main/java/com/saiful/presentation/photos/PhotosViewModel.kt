package com.saiful.presentation.photos

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.saiful.core.components.logger.logDebug
import com.saiful.core.ui.BaseViewModel
import com.saiful.core.ui.ViewEvent
import com.saiful.domain.model.HomeItem
import com.saiful.domain.usecase.GetPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PhotosViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase
) : BaseViewModel<PhotosContract.Event, PhotosContract.Effect>() {

    private val _photoState: MutableStateFlow<PagingData<HomeItem>> =
        MutableStateFlow(value = PagingData.empty())
    val photoState: StateFlow<PagingData<HomeItem>> get() = _photoState

    init {
        logDebug(msg = "logging")
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.Main) {
            getPhotosUseCase(Unit)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _photoState.value = it
                }
        }
    }


    override fun handleEvents(event: ViewEvent) {
        //todo ->
    }

}