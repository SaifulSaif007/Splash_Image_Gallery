package com.saiful.core.data

import com.saiful.core.utils.NetworkStatusTracker
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternetAvailabilityRepository @Inject constructor(
    networkStatusTracker: NetworkStatusTracker
) {
    private val _isConnected = MutableStateFlow(true)
    val isConnected: StateFlow<Boolean> = _isConnected

    init {
        CoroutineScope(Dispatchers.Default).launch {
            networkStatusTracker.networkStatus.collect {
                _isConnected.value = it
            }
        }
    }
}