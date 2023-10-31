package com.saiful.presentation.photos

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saiful.domain.model.HomeItem
import com.saiful.presentation.composables.HomeRowItem

@Composable
internal fun PhotosScreen(
    viewModel: PhotosViewModel = hiltViewModel()
) {

    val uiState by viewModel.viewState.collectAsStateWithLifecycle()

    PhotoScreenContent(photos = uiState.photos)
}

@Composable
private fun PhotoScreenContent(photos: List<HomeItem>) {

    LazyColumn {
        items(photos) { item ->
            HomeRowItem(
                modifier = Modifier,
                homeItem = HomeItem(
                    profileImage = item.profileImage,
                    profileName = item.profileName,
                    sponsored = item.sponsored,
                    mainImage = item.mainImage,
                )
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Preview
@Composable
private fun PhotoScreenPreview() {
    PhotoScreenContent(
        listOf(
            HomeItem(
                profileImage = "",
                profileName = "NEOM",
                sponsored = true,
                mainImage = ""
            ),
            HomeItem(
                profileImage = "",
                profileName = "NEOM",
                sponsored = false,
                mainImage = ""
            )
        )
    )
}