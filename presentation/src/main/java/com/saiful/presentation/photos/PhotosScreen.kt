package com.saiful.presentation.photos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PhotosScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Photos")
    }
}