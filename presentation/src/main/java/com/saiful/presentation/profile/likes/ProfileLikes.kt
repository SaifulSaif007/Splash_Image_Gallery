package com.saiful.presentation.profile.likes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfileLikes(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) {
        Text(text = "Profile Likes")
    }
}