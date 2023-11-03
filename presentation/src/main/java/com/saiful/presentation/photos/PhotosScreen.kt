package com.saiful.presentation.photos

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.saiful.domain.model.HomeItem
import com.saiful.presentation.composables.HomeRowItem

@Composable
internal fun PhotosScreen(
    viewModel: PhotosViewModel = hiltViewModel()
) {

    val uiState by viewModel.viewState.collectAsStateWithLifecycle()
    val photos = viewModel.photoState.collectAsLazyPagingItems()

    PhotoScreenContent(photos = photos)
}

@Composable
private fun PhotoScreenContent(photos: LazyPagingItems<HomeItem>) {

    LazyColumn {
        items(photos.itemCount) { index ->
            HomeRowItem(
                modifier = Modifier,
                homeItem = HomeItem(
                    profileImage = photos[index]!!.profileImage,
                    profileName = photos[index]!!.profileName,
                    sponsored = photos[index]!!.sponsored,
                    mainImage = photos[index]!!.mainImage,
                )
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Preview
@Composable
private fun PhotoScreenPreview() {
}