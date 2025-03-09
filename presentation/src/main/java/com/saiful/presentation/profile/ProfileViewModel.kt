package com.saiful.presentation.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.saiful.core.domain.DomainException
import com.saiful.core.domain.Result
import com.saiful.core.ui.BaseViewModel
import com.saiful.core.ui.ViewEvent
import com.saiful.domain.model.ProfileInfo
import com.saiful.domain.usecase.GetProfileInfoUseCase
import com.saiful.presentation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val profileInfoUseCase: GetProfileInfoUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ProfileContract.Event, ProfileContract.Effect>() {

    private val profile: Routes.Profile = savedStateHandle.toRoute<Routes.Profile>()

    private val userName: String = profile.userName
    val profileName: String = profile.userProfileName

    val uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)

    init {
        loadData(userName)
    }

    private fun loadData(username: String) {
        viewModelScope.launch {
            when (val result = profileInfoUseCase(username)) {
                is Result.Success -> {
                    uiState.value = UIState.Success(result.data)
                }

                is Result.Error -> {
                    uiState.value = UIState.Error(result.error)
                }
            }
        }
    }

    override fun handleEvents(event: ViewEvent) {
        when (event) {
            is ProfileContract.Event.NavigateBack -> {
                setEffect { ProfileContract.Effect.Navigation.NavigateUp }
            }

            is ProfileContract.Event.NavigateToPhotoDetails -> {
                setEffect { ProfileContract.Effect.Navigation.ToPhotoDetails(event.photoId) }
            }

            is ProfileContract.Event.NavigateToCollection -> {
                setEffect {
                    ProfileContract.Effect.Navigation.ToCollectionDetail(
                        collectionId = event.collectionId,
                        title = event.title,
                        desc = event.desc,
                        count = event.count,
                        author = event.author
                    )
                }
            }
        }
    }

    sealed class UIState {
        object Loading : UIState()
        data class Success(val data: ProfileInfo) : UIState()
        data class Error(val exception: DomainException) : UIState()
    }

}