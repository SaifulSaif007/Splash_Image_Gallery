package com.saiful.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saiful.presentation.composables.SearchBar
import com.saiful.presentation.search.photos.SearchPhotoScreen
import kotlinx.coroutines.launch

@Composable
fun SearchHomeScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var searchInput by remember { mutableStateOf("") }

    val tabs = listOf("Photos", "Collections", "Users")
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            SearchBar(
                query = searchInput,
                onQueryChange = { query ->
                    searchInput = query
                },
                onSearch = { query ->
                    searchQuery = query
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.TopCenter
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.padding(16.dp)
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { Text(text = title) }
                    )
                }
            }

            // Horizontal pager content
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp)
            ) { page ->
                when (page) {
                    0 -> SearchPhotoScreen(
                        query = searchQuery,
                    )

                    else -> {
                        //will be updated
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${tabs[page]} Screen -> $searchQuery",
                                fontSize = 18.sp
                            )
                        }
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchHomeScreenPreview() {
    SearchHomeScreen()
}