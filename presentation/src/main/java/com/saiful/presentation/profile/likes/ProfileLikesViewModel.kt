package com.saiful.presentation.profile.likes

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.saiful.core.ui.BaseViewModel
import com.saiful.core.ui.ViewEvent
import com.saiful.domain.model.PhotoItem
import com.saiful.domain.usecase.GetProfileLikedPhotosUseCase
import com.saiful.domain.usecase.userName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileLikesViewModel @Inject constructor(
    private val profileLikedPhotosUseCase: GetProfileLikedPhotosUseCase
) : BaseViewModel<ProfileLikesContract.Event, ProfileLikesContract.Effect>() {

    private val _photoState: MutableStateFlow<PagingData<PhotoItem>> =
        MutableStateFlow(value = PagingData.empty())
    val photoState: StateFlow<PagingData<PhotoItem>> get() = _photoState

    private fun loadData(userName: userName) {
        viewModelScope.launch {
            profileLikedPhotosUseCase(userName)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _photoState.value = it
                }
        }
    }

    override fun handleEvents(event: ViewEvent) {
        when (event) {
            is ProfileLikesContract.Event.Initialize -> loadData(event.username)

            is ProfileLikesContract.Event.SelectPhoto -> {
                setEffect { ProfileLikesContract.Effect.Navigation.ToPhotoDetails(event.photoId) }
            }
        }
    }
}
