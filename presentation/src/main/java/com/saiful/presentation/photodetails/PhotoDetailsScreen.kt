package com.saiful.presentation.photodetails

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.saiful.domain.model.PhotoDetailsItem
import com.saiful.presentation.R
import com.saiful.presentation.composables.ErrorView
import com.saiful.presentation.composables.LoadingView
import com.saiful.presentation.theme.*
import com.saiful.presentation.utils.TestTags
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PhotoDetailsScreen(
    viewModel: PhotoDetailsViewModel = hiltViewModel(),
    onNavigationRequest: (PhotoDetailsContract.Effect) -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.onEach {
            onNavigationRequest(it)
        }.collect()
    }

    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White,
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.setEvent(PhotoDetailsContract.Event.NavigateUp)
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
        when (uiState.value) {
            is PhotoDetailsViewModel.UIState.Loading -> {
                LoadingView(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                )
            }

            is PhotoDetailsViewModel.UIState.Success -> {
                PhotoDetailsScreenContent(
                    photoDetailsItem = (uiState.value as PhotoDetailsViewModel.UIState.Success).photoDetails,
                    onEvent = { viewModel.setEvent(it) },
                )
            }

            is PhotoDetailsViewModel.UIState.Error -> {
                ErrorView(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    onAction = {
                        //viewModel.getPhotoDetails()
                    }
                )
            }
        }
    }

}

@Composable
private fun PhotoDetailsScreenContent(
    photoDetailsItem: PhotoDetailsItem,
    onEvent: (PhotoDetailsContract.Event) -> Unit
) {
    Box {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                SubcomposeAsyncImage(
                    model = photoDetailsItem.mainImage,
                    contentDescription = "main image",
                    loading = {
                        AsyncImage(
                            model = photoDetailsItem.thumbnailImage,
                            contentDescription = "thumbnail",
                            contentScale = ContentScale.Crop,
                        )
                    },
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                )

                Column(
                    modifier = Modifier.padding(6.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 2.dp, vertical = 4.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                onEvent(
                                    PhotoDetailsContract.Event.SelectProfile(
                                        photoDetailsItem.userName,
                                        photoDetailsItem.profileName
                                    )
                                )
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = photoDetailsItem.profileImage,
                            placeholder = painterResource(id = R.drawable.ic_profile),
                            contentDescription = "profile image",
                            modifier = Modifier
                                .size(height = 40.dp, width = 40.dp)
                                .clip(CircleShape)
                                .testTag(TestTags.PROFILE_IMAGE),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = photoDetailsItem.profileName,
                                style = MaterialTheme.typography.titleText,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag(TestTags.PROFILE_NAME)
                            )

                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    HorizontalDivider()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp, vertical = 8.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 6.dp)
                        ) {
                            ImageInfoCell(title = "Camera", info = photoDetailsItem.camera)
                            ImageInfoCell(
                                title = "Focal Length",
                                info = photoDetailsItem.focalLength
                            )
                            ImageInfoCell(title = "ISO", info = photoDetailsItem.iso)
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 6.dp)
                        ) {
                            ImageInfoCell(title = "Aperture", info = photoDetailsItem.aperture)
                            ImageInfoCell(
                                title = "Shutter Speed",
                                info = photoDetailsItem.shutterSpeed
                            )
                            ImageInfoCell(title = "Dimensions", info = photoDetailsItem.dimensions)
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    HorizontalDivider()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            UserInteractionInfoCell(title = "Views", info = photoDetailsItem.views)
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            UserInteractionInfoCell(
                                title = "Downloads",
                                info = photoDetailsItem.downloads
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            UserInteractionInfoCell(title = "Likes", info = photoDetailsItem.likes)
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    HorizontalDivider()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.Absolute.spacedBy(4.dp)
                    ) {
                        for (tags in photoDetailsItem.tags) {
                            SuggestionChip(
                                onClick = { },
                                label = {
                                    Text(
                                        text = tags,
                                        style = MaterialTheme.typography.photoDetailsChip
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
private fun ImageInfoCell(title: String, info: String) {
    Column(
        modifier = Modifier
            .padding(horizontal = 2.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = title, style = MaterialTheme.typography.photoDetailsInfo)
        Text(
            text = info,
            style = MaterialTheme.typography.photoDetailsInfo,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun UserInteractionInfoCell(title: String, info: String) {
    Column(
        modifier = Modifier
            .padding(horizontal = 2.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, style = MaterialTheme.typography.photoDetailsInfo)
        Text(
            text = info,
            style = MaterialTheme.typography.photoDetailsInfo,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PhotoDetailsScreenPreview() {
    SplashGalleryTheme {
        PhotoDetailsScreenContent(
            PhotoDetailsItem(
                profileImage = "https://i.imgur.com/00000000000000000000000000000000.jpg",
                profileName = "John Doe",
                mainImage = "https://i.imgur.com/00000000000000000000000000000001.jpg",
                thumbnailImage = "",
                camera = "Canon EOS 5D Mark IV",
                focalLength = "50mm",
                aperture = "f/2.8",
                shutterSpeed = "1/100",
                iso = "100",
                dimensions = "1920x1080",
                views = "1000",
                downloads = "100",
                likes = "10",
                tags = listOf("nature", "landscape", "mountains"),
                userName = "saiful"
            ),
            onEvent = {}
        )
    }
}