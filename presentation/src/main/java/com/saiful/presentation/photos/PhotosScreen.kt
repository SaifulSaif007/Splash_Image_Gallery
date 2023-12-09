package com.saiful.presentation.photos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.saiful.domain.model.HomeItem
import com.saiful.presentation.composables.*
import kotlinx.coroutines.flow.*

@Composable
internal fun PhotosScreen(
    viewModel: PhotosViewModel = hiltViewModel(),
    navigationRequest: () -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is PhotosContract.Effect.Navigation -> {
                    navigationRequest()
                }
            }
        }.collect()
    }

    val photos = viewModel.photoState.collectAsLazyPagingItems()
    PhotoScreenContent(
        photos = photos
    ) { event -> viewModel.setEvent(event) }
}

@Composable
private fun PhotoScreenContent(
    photos: LazyPagingItems<HomeItem>,
    onEvent: (event: PhotosContract.Event) -> Unit
) {

    LazyColumn {
        items(photos.itemCount) { index ->
            PhotoRowItem(
                homeItem = HomeItem(
                    profileImage = photos[index]!!.profileImage,
                    profileName = photos[index]!!.profileName,
                    sponsored = photos[index]!!.sponsored,
                    mainImage = photos[index]!!.mainImage,
                    mainImageBlurHash = photos[index]!!.mainImageBlurHash,
                    mainImageHeight = photos[index]!!.mainImageHeight,
                    mainImageWidth = photos[index]!!.mainImageWidth
                )
            ) {
                onEvent(PhotosContract.Event.SelectPhoto)
            }

            Spacer(modifier = Modifier.height(10.dp))

        }

        //first time paging data load
        when (photos.loadState.refresh) {
            is LoadState.Error -> this@LazyColumn.item {
                ErrorView(
                    modifier = Modifier.fillParentMaxSize(),
                    onAction = { photos.retry() }
                )
            }

            is LoadState.Loading -> this@LazyColumn.item {
                LoadingView(modifier = Modifier.fillParentMaxSize())
            }

            is LoadState.NotLoading -> {}
        }

        //after first time paging data load
        when (photos.loadState.append) {
            is LoadState.Error -> this@LazyColumn.item {
                ErrorView(
                    modifier = Modifier.fillMaxSize(),
                    onAction = { photos.retry() }
                )
            }

            is LoadState.Loading -> this@LazyColumn.item {
                LoadingView(modifier = Modifier.fillMaxSize())
            }

            is LoadState.NotLoading -> {}
        }

    }
}

@Preview
@Composable
private fun PhotoScreenContentPreview() {
    PhotoScreenContent(
        photos = flowOf(
            PagingData.from(
                data = listOf(
                    HomeItem(
                        profileImage = "",
                        profileName = "NEOM",
                        sponsored = true,
                        mainImage = "",
                        mainImageBlurHash = "",
                        mainImageWidth = 4,
                        mainImageHeight = 3
                    ),
                    HomeItem(
                        profileImage = "",
                        profileName = "NEOM",
                        sponsored = false,
                        mainImage = "",
                        mainImageBlurHash = "",
                        mainImageWidth = 4,
                        mainImageHeight = 3
                    )
                )
            ),
        ).collectAsLazyPagingItems(),
        onEvent = {}
    )
}