package com.saiful.presentation.collections

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
import com.saiful.presentation.composables.*
import com.saiful.presentation.theme.SplashGalleryTheme
import kotlinx.coroutines.flow.*

@Composable
internal fun CollectionsScreen(
    viewModel: CollectionsViewModel = hiltViewModel(),
    navigateCollectionPhotos: (String, String, String, String, String) -> Unit,
    navigateProfile: (String, String) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is CollectionsContract.Effect.Navigation.ToCollectionDetails -> navigateCollectionPhotos(
                    effect.collectionId,
                    effect.collectionName,
                    effect.collectionDesc,
                    effect.totalPhotos,
                    effect.collectionAuthor
                )

                is CollectionsContract.Effect.Navigation.ToProfile -> navigateProfile(
                    effect.userName,
                    effect.profileName
                )
            }
        }.collect()
    }

    val collections = viewModel.collectionState.collectAsLazyPagingItems()
    CollectionScreenContent(
        collections = collections
    ) { event -> viewModel.setEvent(event) }

}

@Composable
private fun CollectionScreenContent(
    collections: LazyPagingItems<CollectionItem>,
    onEvent: (CollectionsContract.Event) -> Unit
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
                        CollectionsContract.Event.SelectCollection(
                            collectionId = collectionId,
                            collectionName = title,
                            collectionDesc = desc,
                            totalPhotos = total.toString(),
                            collectionAuthor = author
                        )
                    )
                },
                onProfileClick = { userName, profileName ->
                    onEvent(CollectionsContract.Event.SelectProfile(userName, profileName))
                }
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        //first time paging data load
        when (collections.loadState.refresh) {
            is LoadState.Error -> this@LazyColumn.item {
                ErrorView(
                    modifier = Modifier.fillParentMaxSize(),
                    onAction = { collections.retry() }
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
                    onAction = { collections.retry() }
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
    SplashGalleryTheme {
        CollectionScreenContent(
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
}