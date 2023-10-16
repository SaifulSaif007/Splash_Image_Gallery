package com.saiful.presentation.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saiful.core.components.logger.logDebug
import com.saiful.domain.usecase.GetPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase
) : ViewModel() {

    init {
        logDebug(msg = "logging")
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.Main) {
            val res = getPhotosUseCase(Unit)
        }
    }

}