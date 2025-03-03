package com.saiful.presentation

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    data object Home : Routes()
}