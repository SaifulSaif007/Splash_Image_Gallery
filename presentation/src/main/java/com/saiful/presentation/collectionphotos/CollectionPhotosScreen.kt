package com.saiful.presentation.collectionphotos

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.saiful.domain.model.PhotoItem
import com.saiful.presentation.composables.ErrorView
import com.saiful.presentation.composables.LoadingView
import com.saiful.presentation.composables.PhotoRowItem
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun CollectionPhotosScreen(
    viewModel: CollectionPhotosViewModel = hiltViewModel()
) {
    val photos = viewModel.photoState.collectAsLazyPagingItems()
    CollectionPhotosScreenContent(photos)
}

@Composable
private fun CollectionPhotosScreenContent(
    photos: LazyPagingItems<PhotoItem>,
) {
    LazyColumn {
        items(photos.itemCount) { index ->
            PhotoRowItem(
                photoItem = PhotoItem(
                    photoId = photos[index]!!.photoId,
                    profileImage = photos[index]!!.profileImage,
                    profileName = photos[index]!!.profileName,
                    sponsored = photos[index]!!.sponsored,
                    mainImage = photos[index]!!.mainImage,
                    mainImageBlurHash = photos[index]!!.mainImageBlurHash,
                    mainImageHeight = photos[index]!!.mainImageHeight,
                    mainImageWidth = photos[index]!!.mainImageWidth
                )
            ) { photoId ->
                //TODO
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
private fun CollectionPhotosScreenPreview() {
    CollectionPhotosScreenContent(
        photos = flowOf(
            PagingData.from(
                data = listOf(
                    PhotoItem(
                        photoId = "1",
                        profileImage = "",
                        profileName = "NEOM",
                        sponsored = true,
                        mainImage = "",
                        mainImageBlurHash = "",
                        mainImageWidth = 4,
                        mainImageHeight = 3
                    ),
                    PhotoItem(
                        photoId = "2",
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
    )
}