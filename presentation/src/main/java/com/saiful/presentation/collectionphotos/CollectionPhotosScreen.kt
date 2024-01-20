package com.saiful.presentation.collectionphotos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.saiful.domain.model.PhotoItem
import com.saiful.presentation.composables.EmptyView
import com.saiful.presentation.composables.ErrorView
import com.saiful.presentation.composables.LoadingView
import com.saiful.presentation.composables.PhotoRowItem
import com.saiful.presentation.theme.collectionInfoSubTitle
import com.saiful.presentation.theme.collectionInfoTitle
import com.saiful.presentation.theme.toolbarText
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CollectionPhotosScreen(
    viewModel: CollectionPhotosViewModel = hiltViewModel(),
    onNavigationRequest: (CollectionPhotosContract.Effect.Navigation) -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is CollectionPhotosContract.Effect.Navigation -> {
                    onNavigationRequest(effect)
                }
            }
        }.collect()
    }

    val photos = viewModel.photoState.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = viewModel.collectionName,
                        style = MaterialTheme.typography.toolbarText
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.setEvent(CollectionPhotosContract.Event.NavigateBack)
                    }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "",
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        CollectionPhotosScreenContent(
            modifier = Modifier.padding(paddingValues),
            collectionDesc = viewModel.collectionDesc,
            collectionTotalPhotos = viewModel.collectionPhotoCount,
            collectionAuthor = viewModel.collectionAuthor,
            photos = photos
        ) { event ->
            viewModel.setEvent(event)
        }
    }
}

@Composable
private fun CollectionPhotosScreenContent(
    modifier: Modifier,
    collectionDesc: String,
    collectionAuthor: String,
    collectionTotalPhotos: String,
    photos: LazyPagingItems<PhotoItem>,
    onEvent: (CollectionPhotosContract.Event) -> Unit
) {
    LazyColumn(modifier) {

        item {
            CollectionInfoView(
                totalPhotos = collectionTotalPhotos,
                collectionAuthor = collectionAuthor,
                collectionDesc = collectionDesc,
                modifier = Modifier
            )
        }

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
                onEvent(CollectionPhotosContract.Event.SelectPhoto(photoId))
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

            is LoadState.NotLoading -> {
                item {
                    if (photos.itemCount == 0) {
                        EmptyView(
                            modifier = Modifier.fillParentMaxSize().padding(bottom = 140.dp)
                        )
                    }
                }
            }
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

@Composable
private fun CollectionInfoView(
    modifier: Modifier,
    collectionDesc: String,
    totalPhotos: String,
    collectionAuthor: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 1.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (collectionDesc.isNotBlank()) {
            Text(
                text = collectionDesc,
                style = MaterialTheme.typography.collectionInfoTitle,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            )
        }
        Text(
            text = "$totalPhotos photos \u2022 $collectionAuthor",
            style = MaterialTheme.typography.collectionInfoSubTitle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}

@Preview
@Composable
private fun CollectionPhotosScreenPreview() {
    CollectionPhotosScreenContent(
        modifier = Modifier,
        collectionDesc = "Description",
        collectionTotalPhotos = "12",
        collectionAuthor = "Author",
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
        onEvent = {}
    )
}