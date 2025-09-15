package com.saiful.presentation.search.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.saiful.domain.model.SearchUserItem
import com.saiful.presentation.composables.ErrorView
import com.saiful.presentation.composables.LoadingView
import com.saiful.presentation.composables.SearchUserRowItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
internal fun SearchUserScreen(
    query: String,
    viewModel: SearchUserViewModel = hiltViewModel()
) {

    //optimize for search query
    LaunchedEffect(key1 = query) {
        viewModel.setEvent(SearchUserContract.Event.SearchUser(query))
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.onEach {

        }.collect()
    }


    val users = viewModel.userState.collectAsLazyPagingItems()

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
        SearchUsersContent(users = users)

    }
}

@Composable
private fun SearchUsersContent(users: LazyPagingItems<SearchUserItem>) {
    LazyColumn {
        items(users.itemCount) { index ->
            users[index]?.let { user ->
                SearchUserRowItem(
                    user = user,
                )
            }
        }

        when (users.loadState.refresh) {
            is LoadState.Error -> this@LazyColumn.item {
                ErrorView(
                    modifier = Modifier.fillParentMaxSize(),
                    onAction = { users.retry() },
                    errorMsg = (users.loadState.refresh as LoadState.Error).error.message.toString()
                )
            }

            is LoadState.Loading -> this@LazyColumn.item {
                LoadingView(modifier = Modifier.fillParentMaxSize())
            }

            is LoadState.NotLoading -> {}
        }

        //after first time paging data load
        when (users.loadState.append) {
            is LoadState.Error -> this@LazyColumn.item {
                ErrorView(
                    modifier = Modifier.fillMaxSize(),
                    onAction = { users.retry() },
                    errorMsg = (users.loadState.refresh as LoadState.Error).error.message.toString()
                )
            }

            is LoadState.Loading -> this@LazyColumn.item {
                LoadingView(modifier = Modifier.fillMaxSize())
            }

            is LoadState.NotLoading -> {}
        }
    }
}