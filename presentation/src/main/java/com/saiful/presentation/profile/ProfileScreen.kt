package com.saiful.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.saiful.domain.mapper.COLLECTIONS
import com.saiful.domain.mapper.LIKES
import com.saiful.domain.mapper.PHOTOS
import com.saiful.domain.model.ProfileInfo
import com.saiful.domain.usecase.userName
import com.saiful.presentation.R
import com.saiful.presentation.composables.ErrorView
import com.saiful.presentation.composables.LoadingView
import com.saiful.presentation.composables.nestedscroll.VerticalNestedScrollView
import com.saiful.presentation.composables.nestedscroll.rememberNestedScrollViewState
import com.saiful.presentation.profile.collection.ProfileCollectionScreen
import com.saiful.presentation.profile.likes.ProfileLikesScreen
import com.saiful.presentation.profile.photos.ProfilePhotoScreen
import com.saiful.presentation.theme.SplashGalleryTheme
import com.saiful.presentation.utils.TestTags
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    profileUserName: userName,
    navigationRequest: (ProfileContract.Effect) -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.onEach {
            navigationRequest(it)
        }.collect()
    }

    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = viewModel.profileName,
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.setEvent(ProfileContract.Event.NavigateBack)
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "",
                        )
                    }
                },
            )

        },
    ) { padding ->
        when (uiState.value) {
            is ProfileViewModel.UIState.Error -> {
                ErrorView(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize(),
                    onAction = {
                        //todo
                    }
                )
            }

            ProfileViewModel.UIState.Loading -> {
                LoadingView(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                )
            }

            is ProfileViewModel.UIState.Success -> {
                ProfileScreenContent(
                    modifier = Modifier.padding(padding),
                    profileInfo = (uiState.value as ProfileViewModel.UIState.Success).data,
                    userName = profileUserName
                ) {
                    viewModel.setEvent(it)
                }
            }
        }
    }
}


@Composable
private fun ProfileScreenContent(
    modifier: Modifier,
    profileInfo: ProfileInfo,
    userName: userName,
    event: (ProfileContract.Event) -> Unit
) {
    Column(modifier) {
        val nestedScrollViewState = rememberNestedScrollViewState()
        VerticalNestedScrollView(
            state = nestedScrollViewState,
            header = {
                UserInfoSection(profileInfo)
            },
            content = {
                PagerSection(profileInfo, userName, event)
            }
        )
    }

}

@Composable
private fun PagerSection(
    profileInfo: ProfileInfo,
    userName: userName,
    event: (ProfileContract.Event) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val tabs = profileInfo.visibleTabs
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabs.size }
    )

    Column {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            text = title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleSmall
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    })
            }
        }

        HorizontalPager(state = pagerState) { page ->
            when (tabs[page]) {
                PHOTOS -> ProfilePhotoScreen(userName,
                    navigateToPhotoDetails = { photoId ->
                        event(ProfileContract.Event.NavigateToPhotoDetails(photoId))
                    }
                )

                LIKES -> ProfileLikesScreen(userName,
                    navigateToPhotoDetails = { photoId ->
                        event(ProfileContract.Event.NavigateToPhotoDetails(photoId))
                    }
                )

                COLLECTIONS -> ProfileCollectionScreen(
                    userName,
                    navigateCollectionPhotos = { collectionId, name, desc, total, author ->
                        event(
                            ProfileContract.Event.NavigateToCollection(
                                collectionId = collectionId,
                                title = name,
                                desc = desc,
                                count = total,
                                author = author
                            )
                        )
                    },
                )
            }
        }
    }
}

@Composable
private fun UserInfoSection(profileInfo: ProfileInfo) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            AsyncImage(
                model = profileInfo.profileImage,
                placeholder = painterResource(id = R.drawable.ic_profile),
                contentDescription = "profile image",
                modifier = Modifier
                    .size(height = 60.dp, width = 60.dp)
                    .clip(CircleShape)
                    .testTag(TestTags.PROFILE_IMAGE),
                contentScale = ContentScale.Crop
            )

            UserInfoCell(
                title = "Photos",
                info = profileInfo.photos
            )
            UserInfoCell(
                title = "Likes",
                info = profileInfo.likes
            )
            UserInfoCell(
                title = "Collection",
                info = profileInfo.collection
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Text(
                text = profileInfo.profileName,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = profileInfo.location,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = profileInfo.bio,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }
    }
}

@Composable
private fun UserInfoCell(title: String, info: String) {
    Column(
        modifier = Modifier
            .padding(horizontal = 2.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Text(
            text = info,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    SplashGalleryTheme {
        ProfileScreenContent(
            modifier = Modifier,
            profileInfo = ProfileInfo(
                profileName = "John Doe",
                location = "New York",
                bio = "Software Engineer",
                profileImage = "https://example.com/profile.jpg",
                photos = "100",
                likes = "500",
                collection = "Art",
                visibleTabs = listOf("Photos", "Likes")
            ),
            userName = "saiful",
            event = {}
        )
    }
}

