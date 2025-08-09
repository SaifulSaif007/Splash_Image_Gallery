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
import com.saiful.domain.model.PhotoItem
import com.saiful.domain.usecase.photoId
import com.saiful.domain.usecase.userName
import com.saiful.presentation.composables.*
import com.saiful.presentation.theme.SplashGalleryTheme
import kotlinx.coroutines.flow.*

@Composable
internal fun PhotosScreen(
    viewModel: PhotosViewModel = hiltViewModel(),
    navigatePhotoDetails: (photoId) -> Unit,
    navigateProfile: (userName, String) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is PhotosContract.Effect.Navigation.ToPhotoDetails -> {
                    navigatePhotoDetails(effect.photoId)
                }

                is PhotosContract.Effect.Navigation.ToProfile -> {
                    navigateProfile(effect.userName, effect.profileName)
                }
            }
        }.collect()
    }

    val photos = viewModel.photoState.collectAsLazyPagingItems()

    PhotoScreenContent(
        modifier = Modifier,
        photos = photos
    ) { event -> viewModel.setEvent(event) }

}

@Composable
private fun PhotoScreenContent(
    modifier: Modifier = Modifier,
    photos: LazyPagingItems<PhotoItem>,
    onEvent: (event: PhotosContract.Event) -> Unit
) {

    LazyColumn(modifier = modifier) {
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
                    mainImageWidth = photos[index]!!.mainImageWidth,
                    profileUserName = photos[index]!!.profileUserName
                ),
                onItemClick = { photoId ->
                    onEvent(PhotosContract.Event.SelectPhoto(photoId))
                },
                onProfileClick = { userName, profileName ->
                    onEvent(PhotosContract.Event.SelectProfile(userName, profileName))
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

        }

        //first time paging data load
        when (photos.loadState.refresh) {
            is LoadState.Error -> this@LazyColumn.item {
                ErrorView(
                    modifier = Modifier.fillParentMaxSize(),
                    onAction = { photos.retry() },
                    errorMsg = (photos.loadState.refresh as LoadState.Error).error.message.toString()
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
                    onAction = { photos.retry() },
                    errorMsg = (photos.loadState.refresh as LoadState.Error).error.message.toString()
                )
            }

            is LoadState.Loading -> this@LazyColumn.item {
                LoadingView(modifier = Modifier.fillMaxSize())
            }

            is LoadState.NotLoading -> {}
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PhotoScreenContentPreview() {
    SplashGalleryTheme {
        PhotoScreenContent(
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
                            mainImageHeight = 3,
                            profileUserName = "saiful"
                        ),
                        PhotoItem(
                            photoId = "2",
                            profileImage = "",
                            profileName = "NEOM",
                            sponsored = false,
                            mainImage = "",
                            mainImageBlurHash = "",
                            mainImageWidth = 4,
                            mainImageHeight = 3,
                            profileUserName = "saiful"
                        )
                    )
                ),
            ).collectAsLazyPagingItems(),
            onEvent = {}
        )
    }
}