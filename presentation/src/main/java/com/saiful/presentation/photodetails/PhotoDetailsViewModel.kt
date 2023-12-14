package com.saiful.presentation.photodetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.saiful.core.components.logger.logDebug
import com.saiful.core.components.logger.logError
import com.saiful.core.domain.Result
import com.saiful.core.ui.BaseViewModel
import com.saiful.core.ui.ViewEvent
import com.saiful.domain.usecase.GetPhotoDetailsUseCase
import com.saiful.domain.usecase.photoId
import com.saiful.presentation.utils.Constants.PHOTO_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PhotoDetailsViewModel @Inject constructor(
    private val photoDetailsUseCase: GetPhotoDetailsUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<PhotoDetailsContract.Event, PhotoDetailsContract.Effect>() {

    private val userId: String = checkNotNull(savedStateHandle[PHOTO_ID])

    init {
        getPhotoDetails(userId)
    }

    private fun getPhotoDetails(photoId: photoId) {
        viewModelScope.launch {
            when (val result = photoDetailsUseCase(photoId)) {
                is Result.Success -> logDebug(msg = result.data.toString())
                is Result.Error -> logError(msg = result.error.error)
            }
        }
    }

    override fun handleEvents(event: ViewEvent) {}

}