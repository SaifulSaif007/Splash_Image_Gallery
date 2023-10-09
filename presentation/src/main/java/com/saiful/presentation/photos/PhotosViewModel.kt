package com.saiful.presentation.photos

import androidx.lifecycle.ViewModel
import com.saiful.core.components.logger.logDebug
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor() : ViewModel() {

    init {
        logDebug(msg = "logging")
    }

}