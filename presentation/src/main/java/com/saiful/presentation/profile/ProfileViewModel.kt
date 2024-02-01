package com.saiful.presentation.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.saiful.core.components.logger.logDebug
import com.saiful.core.components.logger.logError
import com.saiful.core.domain.Result
import com.saiful.core.ui.BaseViewModel
import com.saiful.core.ui.ViewEvent
import com.saiful.domain.usecase.GetProfileInfoUseCase
import com.saiful.presentation.utils.Constants.USER_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val profileInfoUseCase: GetProfileInfoUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ProfileContract.Event, ProfileContract.Effect>() {

    private val userName: String = checkNotNull(savedStateHandle[USER_NAME])

    init {
        loadData(userName)
    }

    private fun loadData(username: String) {
        viewModelScope.launch {
            when (val result = profileInfoUseCase(username)) {
                is Result.Success -> {
                    logDebug(msg = result.data.toString())
                }

                is Result.Error -> {
                    logError(msg = result.error.toString())
                }
            }
        }
    }

    override fun handleEvents(event: ViewEvent) {
        //todo
    }

}