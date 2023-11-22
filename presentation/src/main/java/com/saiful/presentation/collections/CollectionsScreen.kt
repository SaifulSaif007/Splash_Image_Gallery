package com.saiful.presentation.collections

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.saiful.domain.model.CollectionItem
import com.saiful.presentation.composables.*
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun CollectionsScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Collections")
    }
}

@Composable
fun CollectionScreenContent(collections: LazyPagingItems<CollectionItem>) {

    LazyColumn {
        items(collections.itemCount) { index ->
            CollectionRowItem(
                collectionItem = CollectionItem(
                    profileImage = collections[index]!!.profileImage,
                    profileName = collections[index]!!.profileName,
                    mainImage = collections[index]!!.mainImage,
                    title = collections[index]!!.title,
                    totalPhoto = collections[index]!!.totalPhoto
                )
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
                    modifier = Modifier.fillParentMaxSize(),
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


@Preview
@Composable
private fun CollectionScreenContentPreview() {
    CollectionScreenContent(
        collections =
        flowOf(
            PagingData.from(
                listOf(
                    CollectionItem(
                        mainImage = "",
                        profileImage = "",
                        profileName = "NEOM",
                        title = "City",
                        totalPhoto = 10
                    ),
                    CollectionItem(
                        mainImage = "",
                        profileImage = "",
                        profileName = "ABC",
                        title = "Adventure",
                        totalPhoto = 101
                    )
                )
            )
        ).collectAsLazyPagingItems()
    )
}