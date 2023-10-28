package com.saiful.presentation.photos

import androidx.lifecycle.viewModelScope
import com.saiful.core.components.logger.logDebug
import com.saiful.core.components.logger.logError
import com.saiful.core.domain.Result
import com.saiful.core.ui.BaseViewModel
import com.saiful.core.ui.ViewEvent
import com.saiful.domain.usecase.GetPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PhotosViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase
) : BaseViewModel<PhotosContract.Event, PhotosContract.State, PhotosContract.Effect>() {

    init {
        logDebug(msg = "logging")
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.Main) {
            when (val res = getPhotosUseCase(Pair(first = 1, second = 10))) {
                is Result.Success -> {
                    setState {
                        copy(
                            loading = false,
                            photos = res.data
                        )
                    }
                }

                is Result.Error -> {
                    logError(msg = res.error.message)
                }
            }
        }
    }

    override fun setInitialState(): PhotosContract.State = PhotosContract.State(loading = true)

    override fun handleEvents(event: ViewEvent) {
        //todo ->
    }

}