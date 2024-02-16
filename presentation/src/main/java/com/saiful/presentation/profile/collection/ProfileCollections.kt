package com.saiful.presentation.profile.collection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.saiful.domain.usecase.userName

@Composable
fun ProfileCollections(userName: userName, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) {
        Text(text = "Profile Collections")
    }
}