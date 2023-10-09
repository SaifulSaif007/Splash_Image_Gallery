package com.saiful.presentation.photos

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.saiful.presentation.composables.HomeRowItem
import com.saiful.presentation.model.HomeItem

@Composable
fun PhotosScreen(
    viewModel: PhotosViewModel = hiltViewModel()
) {

    LazyColumn {
        items(10) {
            HomeRowItem(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
                homeItem = HomeItem(
                    profileImage = "https://images.unsplash.com/profile-1679489218992-ebe823c797dfimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32",
                    profileName = "NEOM",
                    sponsored = true,
                    mainImage = "https://images.unsplash.com/photo-1682905926517-6be3768e29f0?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3wxNzQ1NDV8MXwxfGFsbHwxfHx8fHx8Mnx8MTY5NTU3Mzk2OXw&ixlib=rb-4.0.3&q=80&w=200",
                )
            )
        }
    }
}

@Preview
@Composable
private fun PhotoScreenPreview() {
    PhotosScreen()
}