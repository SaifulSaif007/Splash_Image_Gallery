package com.saiful.presentation.profile.collection

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
import com.saiful.domain.model.CollectionItem
import com.saiful.domain.usecase.userName
import com.saiful.presentation.composables.*
import kotlinx.coroutines.flow.*

@Composable
internal fun ProfileCollectionScreen(
    userName: userName,
    navigateCollectionPhotos: (String, String, String, String, String) -> Unit,
    viewModel: ProfileCollectionViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.setEvent(ProfileCollectionContract.Event.Initialize(userName))

        viewModel.effect.onEach {
            when (it) {
                is ProfileCollectionContract.Effect.Navigation.ToCollectionDetails -> {
                    navigateCollectionPhotos(
                        it.collectionId,
                        it.collectionName,
                        it.collectionDesc,
                        it.totalPhotos,
                        it.collectionAuthor
                    )
                }
            }
        }.collect()
    }

    val collections = viewModel.collectionState.collectAsLazyPagingItems()

    ProfileCollectionScreenContent(collections = collections) { event ->
        viewModel.setEvent(event)
    }
}


@Composable
private fun ProfileCollectionScreenContent(
    collections: LazyPagingItems<CollectionItem>,
    onEvent: (ProfileCollectionContract.Event) -> Unit
) {

    LazyColumn {
        items(collections.itemCount) { index ->
            CollectionRowItem(
                collectionItem = CollectionItem(
                    collectionId = collections[index]!!.collectionId,
                    profileImage = collections[index]!!.profileImage,
                    profileName = collections[index]!!.profileName,
                    mainImage = collections[index]!!.mainImage,
                    mainImageBlurHash = collections[index]!!.mainImageBlurHash,
                    mainImageHeight = collections[index]!!.mainImageHeight,
                    mainImageWidth = collections[index]!!.mainImageWidth,
                    title = collections[index]!!.title,
                    description = collections[index]!!.description,
                    totalPhoto = collections[index]!!.totalPhoto,
                    profileUserName = collections[index]!!.profileUserName
                ),
                onItemClick = { collectionId, title, desc, total, author ->
                    onEvent(
                        ProfileCollectionContract.Event.SelectCollection(
                            collectionId = collectionId,
                            collectionName = title,
                            collectionDesc = desc,
                            totalPhotos = total.toString(),
                            collectionAuthor = author
                        )
                    )
                },
                profileSectionVisible = false
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        //first time paging data load
        when (collections.loadState.refresh) {
            is LoadState.Error -> this@LazyColumn.item {
                ErrorView(
                    modifier = Modifier.fillParentMaxSize(),
                    onAction = { collections.retry() },
                    errorMsg = (collections.loadState.refresh as LoadState.Error).error.message.toString()
                )
            }

            is LoadState.Loading -> this@LazyColumn.item {
                LoadingView(modifier = Modifier.fillParentMaxSize())
            }

            is LoadState.NotLoading -> {}
        }

        //after first time paging data load
        when (collections.loadState.append) {
            is LoadState.Error -> this@LazyColumn.item {
                ErrorView(
                    modifier = Modifier.fillMaxSize(),
                    onAction = { collections.retry() },
                    errorMsg = (collections.loadState.refresh as LoadState.Error).error.message.toString()
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
private fun CollectionScreenContentPreview() {
    ProfileCollectionScreenContent(
        collections =
            flowOf(
                PagingData.from(
                    listOf(
                        CollectionItem(
                            collectionId = "1",
                            mainImage = "",
                            mainImageBlurHash = "",
                            mainImageHeight = 3,
                            mainImageWidth = 4,
                            profileImage = "",
                            profileName = "NEOM",
                            title = "City",
                            description = "desc",
                            totalPhoto = 10,
                            profileUserName = "saiful"
                        ),
                        CollectionItem(
                            collectionId = "2",
                            mainImage = "",
                            mainImageBlurHash = "",
                            mainImageHeight = 3,
                            mainImageWidth = 4,
                            profileImage = "",
                            profileName = "ABC",
                            title = "Adventure",
                            description = "",
                            totalPhoto = 101,
                            profileUserName = "saiful"
                        )
                    )
                )
            ).collectAsLazyPagingItems(),
        onEvent = {}
    )
}