package com.saiful.presentation.search.photos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.saiful.domain.model.PhotoItem
import com.saiful.presentation.composables.ErrorView
import com.saiful.presentation.composables.LoadingView
import com.saiful.presentation.composables.PhotoRowItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach

@Composable
internal fun SearchPhotoScreen(
    query: String,
    onNavigateProfile: (String, String) -> Unit,
    onNavigatePhotoDetails: (String) -> Unit,
    viewModel: SearchPhotoViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = query) {
        viewModel.setEvent(SearchPhotoContract.Event.SearchPhoto(query = query))
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.onEach {
            when (it) {
                is SearchPhotoContract.Effect.Navigation.ToPhotoDetails -> {
                    onNavigatePhotoDetails(it.photoId)
                }

                is SearchPhotoContract.Effect.Navigation.ToProfile -> {
                    onNavigateProfile(it.userName, it.profileName)
                }
            }

        }.collect()
    }

    val photos = viewModel.photoState.collectAsLazyPagingItems()

    if (viewModel.currentQuery.collectAsState().value == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Try searching something",
                fontSize = 18.sp
            )
        }
    } else {
        SearchPhotosContent(photos = photos) { event: SearchPhotoContract.Event ->
            viewModel.setEvent(event)
        }
    }

}

@Composable
private fun SearchPhotosContent(
    photos: LazyPagingItems<PhotoItem>,
    event: (SearchPhotoContract.Event) -> Unit
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
                    onItemClick = {
                        event(SearchPhotoContract.Event.SelectPhoto(photoId = it))
                    },
                    onProfileClick = { userName, name ->
                        event(SearchPhotoContract.Event.SelectProfile(userName, name))
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
internal fun SearchPhotosPreview() {
    SearchPhotosContent(
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