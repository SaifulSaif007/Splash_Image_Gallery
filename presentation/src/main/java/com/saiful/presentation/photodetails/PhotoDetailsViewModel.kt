package com.saiful.presentation.photodetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.saiful.core.components.logger.logError
import com.saiful.core.domain.Result
import com.saiful.core.ui.BaseViewModel
import com.saiful.core.ui.ViewEvent
import com.saiful.domain.model.PhotoDetailsItem
import com.saiful.domain.usecase.GetPhotoDetailsUseCase
import com.saiful.domain.usecase.photoId
import com.saiful.presentation.utils.Constants.PHOTO_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PhotoDetailsViewModel @Inject constructor(
    private val photoDetailsUseCase: GetPhotoDetailsUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<PhotoDetailsContract.Event, PhotoDetailsContract.Effect>() {

    private val userId: String = checkNotNull(savedStateHandle[PHOTO_ID])
    private val _photoDetails: MutableStateFlow<PhotoDetailsItem?> =
        MutableStateFlow(value = null)

    val uiState: MutableStateFlow<UIState> = MutableStateFlow(value = UIState.Loading)

    init {
        getPhotoDetails(userId)
    }

    private fun getPhotoDetails(photoId: photoId) {
        viewModelScope.launch {
            when (val result = photoDetailsUseCase(photoId)) {
                is Result.Success -> {
                    _photoDetails.value = result.data
                }

                is Result.Error -> logError(msg = result.error.error)
            }

            uiState.value = if (_photoDetails.value != null) {
                UIState.Success(photoDetails = _photoDetails.value!!)
            } else {
                UIState.Error
            }
        }
    }

    override fun handleEvents(event: ViewEvent) {}

}

sealed class UIState {
    object Loading : UIState()
    data class Success(
        val photoDetails: PhotoDetailsItem
    ) : UIState()

    object Error : UIState()
}