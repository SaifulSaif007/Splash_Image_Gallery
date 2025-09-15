package com.saiful.presentation.search.collections

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
import com.saiful.domain.model.CollectionItem
import com.saiful.presentation.composables.CollectionRowItem
import com.saiful.presentation.composables.ErrorView
import com.saiful.presentation.composables.LoadingView
import com.saiful.presentation.theme.SplashGalleryTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach

@Composable
internal fun SearchCollectionScreen(
    query: String,
    viewModel: SearchCollectionViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = query) {
        viewModel.setEvent(SearchCollectionContract.Event.SearchCollection(query))
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.onEach {
            when (it) {
                is SearchCollectionContract.Effect.Navigation.ToCollectionDetails -> {}
                is SearchCollectionContract.Effect.Navigation.ToProfile -> {}
            }
        }.collect()
    }


    val collections = viewModel.collectionState.collectAsLazyPagingItems()

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
        SearchCollectionScreenContent(
            modifier = Modifier,
            collections = collections
        ) { event ->
            viewModel.setEvent(event)
        }
    }


}


@Composable
private fun SearchCollectionScreenContent(
    modifier: Modifier = Modifier,
    collections: LazyPagingItems<CollectionItem>,
    onEvent: (SearchCollectionContract.Event) -> Unit
) {
    LazyColumn(modifier = modifier) {
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
                        SearchCollectionContract.Event.SelectCollection(
                            collectionId = collectionId,
                            collectionName = title,
                            collectionDesc = desc,
                            totalPhotos = total.toString(),
                            collectionAuthor = author
                        )
                    )
                },
                onProfileClick = { userName, profileName ->
                    onEvent(SearchCollectionContract.Event.SelectProfile(userName, profileName))
                }
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
private fun SearchCollectionScreenContentPreview() {
    SplashGalleryTheme {
        SearchCollectionScreenContent(
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