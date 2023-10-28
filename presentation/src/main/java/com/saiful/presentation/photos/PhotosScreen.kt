package com.saiful.presentation.photos

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saiful.domain.model.HomeItem
import com.saiful.presentation.composables.HomeRowItem

@Composable
internal fun PhotosScreen(
    viewModel: PhotosViewModel = hiltViewModel()
) {

    val uiState by viewModel.viewState.collectAsStateWithLifecycle()

    LazyColumn {
        items(uiState.photos) { item ->
            HomeRowItem(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
                homeItem = HomeItem(
                    profileImage = item.profileImage,
                    profileName = item.profileName,
                    sponsored = item.sponsored,
                    mainImage = item.mainImage,
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