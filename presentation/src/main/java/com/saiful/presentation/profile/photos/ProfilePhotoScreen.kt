package com.saiful.presentation.profile.photos

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
import kotlinx.coroutines.flow.*

@Composable
fun ProfilePhotoScreen(
    userName: userName,
    navigateToPhotoDetails: (photoId) -> Unit,
    viewModel: ProfilePhotosViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.setEvent(ProfilePhotosContract.Event.Initialize(userName = userName))

        viewModel.effect.onEach {
            when (it) {
                is ProfilePhotosContract.Effect.Navigation.ToPhotoDetails -> {
                    navigateToPhotoDetails(it.photoId)
                }
            }
        }.collect()
    }

    val photos = viewModel.photoState.collectAsLazyPagingItems()

    ProfilePhotosContent(photos = photos) { event: ProfilePhotosContract.Event ->
        viewModel.setEvent(event)
    }
}

@Composable
private fun ProfilePhotosContent(
    photos: LazyPagingItems<PhotoItem>,
    event: (ProfilePhotosContract.Event) -> Unit
) {
    LazyColumn {
        items(photos.itemCount) { index ->
            photos[index]?.let { photo ->
                PhotoRowItem(
                    photoItem = PhotoItem(
                        photoId = photo.photoId,
                        profileImage = photo.profileImage,
                        profileName = photo.profileName,
                        sponsored = photo.sponsored,
                        mainImage = photo.mainImage,
                        mainImageBlurHash = photo.mainImageBlurHash,
                        mainImageHeight = photo.mainImageHeight,
                        mainImageWidth = photo.mainImageWidth,
                        profileUserName = photo.profileUserName
                    ),
                    profileSectionVisible = false,
                    onItemClick = {
                        event(ProfilePhotosContract.Event.SelectPhoto(photoId = it))
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

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
private fun ProfilePhotosPreview() {
    ProfilePhotosContent(
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
        event = {}
    )
}