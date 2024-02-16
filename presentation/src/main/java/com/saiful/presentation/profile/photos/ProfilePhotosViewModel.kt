package com.saiful.presentation.profile.photos

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.saiful.core.ui.BaseViewModel
import com.saiful.core.ui.ViewEvent
import com.saiful.domain.model.PhotoItem
import com.saiful.domain.usecase.GetProfilePhotosUseCase
import com.saiful.domain.usecase.userName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilePhotosViewModel @Inject constructor(
    private val profilePhotosUseCase: GetProfilePhotosUseCase
) : BaseViewModel<ProfilePhotosContract.Event, ProfilePhotosContract.Effect>() {


    private val _photoState: MutableStateFlow<PagingData<PhotoItem>> =
        MutableStateFlow(value = PagingData.empty())
    val photoState: StateFlow<PagingData<PhotoItem>> get() = _photoState


    private fun loadData(userName: userName) {
        viewModelScope.launch {
            profilePhotosUseCase(userName)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _photoState.value = it
                }
        }
    }


    override fun handleEvents(event: ViewEvent) {
        when (event) {
            is ProfilePhotosContract.Event.Initialize -> {
                loadData(event.userName)
            }

            is ProfilePhotosContract.Event.SelectPhoto -> {
                setEffect { ProfilePhotosContract.Effect.Navigation.ToPhotoDetails(event.photoId) }
            }
        }
    }
}