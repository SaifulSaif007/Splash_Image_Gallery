package com.saiful.presentation.photodetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.saiful.core.domain.DomainException
import com.saiful.core.domain.Result
import com.saiful.core.ui.BaseViewModel
import com.saiful.core.ui.ViewEvent
import com.saiful.domain.model.PhotoDetailsItem
import com.saiful.domain.usecase.GetPhotoDetailsUseCase
import com.saiful.domain.usecase.photoId
import com.saiful.presentation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PhotoDetailsViewModel @Inject constructor(
    private val photoDetailsUseCase: GetPhotoDetailsUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<PhotoDetailsContract.Event, PhotoDetailsContract.Effect>() {

    private val photoDetails: Routes.PhotoDetails = savedStateHandle.toRoute()

    val uiState: MutableStateFlow<UIState> = MutableStateFlow(value = UIState.Loading)

    init {
        getPhotoDetails(photoDetails.photoId)
    }

    private fun getPhotoDetails(photoId: photoId) {
        viewModelScope.launch {
            when (val result = photoDetailsUseCase(photoId)) {
                is Result.Success -> {
                    uiState.value = UIState.Success(photoDetails = result.data)
                }

                is Result.Error -> {
                    uiState.value = UIState.Error(result.error)
                }
            }

        }
    }

    override fun handleEvents(event: ViewEvent) {
        when (event) {
            is PhotoDetailsContract.Event.NavigateUp -> {
                setEffect { PhotoDetailsContract.Effect.Navigation.NavigateUp }
            }

            is PhotoDetailsContract.Event.SelectProfile -> {
                setEffect {
                    PhotoDetailsContract.Effect.Navigation.ToProfile(
                        event.userName,
                        event.profileName
                    )
                }
            }
        }
    }

    sealed class UIState {
        object Loading : UIState()
        data class Success(
            val photoDetails: PhotoDetailsItem
        ) : UIState()

        data class Error(val exception: DomainException) : UIState()
    }
}