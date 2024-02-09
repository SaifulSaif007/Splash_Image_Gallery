package com.saiful.presentation.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.saiful.core.components.logger.logDebug
import com.saiful.core.domain.DomainException
import com.saiful.core.domain.Result
import com.saiful.core.ui.BaseViewModel
import com.saiful.core.ui.ViewEvent
import com.saiful.domain.model.ProfileInfo
import com.saiful.domain.usecase.GetProfileInfoUseCase
import com.saiful.presentation.utils.Constants.USER_NAME
import com.saiful.presentation.utils.Constants.USER_PROFILE_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val profileInfoUseCase: GetProfileInfoUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ProfileContract.Event, ProfileContract.Effect>() {

    private val userName: String = checkNotNull(savedStateHandle[USER_NAME])
    private val profileName: String = checkNotNull(savedStateHandle[USER_PROFILE_NAME])

    val uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)

    init {
        loadData(userName)
    }

    private fun loadData(username: String) {
        viewModelScope.launch {
            when (val result = profileInfoUseCase(username)) {
                is Result.Success -> {
                    uiState.value = UIState.Success(result.data)
                    logDebug(msg = result.data.toString())
                }

                is Result.Error -> {
                    uiState.value = UIState.Error(result.error)
                    logDebug(msg = result.error.toString())
                }
            }
        }
    }

    override fun handleEvents(event: ViewEvent) {
        //todo
    }

    sealed class UIState {
        object Loading : UIState()
        data class Success(val data: ProfileInfo) : UIState()
        data class Error(val exception: DomainException) : UIState()
    }

}