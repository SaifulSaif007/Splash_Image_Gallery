package com.saiful.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.saiful.core.domain.DomainException
import com.saiful.presentation.collections.CollectionsScreen
import com.saiful.presentation.photos.PhotosScreen
import com.saiful.presentation.theme.primaryText
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onError: (DomainException) -> Unit,
    onNavigationRequest: (HomeContract.Effect.Navigation) -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.onEach {
            when (it) {
                is HomeContract.Effect.Navigation -> onNavigationRequest(it)
            }
        }.collect()
    }

    val tabs = LocalContext.current.resources.getStringArray(R.array.dashboardTabTitle)
    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabs.size }
    )

    Column {
        TabRow(
            modifier = Modifier,
            selectedTabIndex = pagerState.currentPage
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            text = title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.primaryText
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }

        }

        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> PhotosScreen(
                    navigatePhotoDetails = {
                        viewModel.setEvent(HomeContract.Event.SelectPhoto(it))
                    },
                    navigateProfile = { userName, profileName ->
                        viewModel.setEvent(HomeContract.Event.SelectProfile(userName, profileName))
                    }
                )

                1 -> CollectionsScreen(
                    navigateCollectionPhotos = { collectionId, name, desc, total, author ->
                        viewModel.setEvent(
                            HomeContract.Event.SelectCollection(
                                collectionId = collectionId,
                                title = name,
                                desc = desc,
                                count = total,
                                author = author
                            )
                        )
                    },
                    navigateProfile = { userName, profileName ->
                        viewModel.setEvent(HomeContract.Event.SelectProfile(userName, profileName))
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onError = {},
        onNavigationRequest = {}
    )
}