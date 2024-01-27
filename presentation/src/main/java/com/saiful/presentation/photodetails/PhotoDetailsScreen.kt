package com.saiful.presentation.photodetails

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.saiful.presentation.theme.AppColor
import com.saiful.presentation.theme.photoDetailsChip
import com.saiful.presentation.theme.photoDetailsInfo
import com.saiful.presentation.theme.titleText
import com.saiful.presentation.utils.TestTags
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PhotoDetailsScreen(
    viewModel: PhotoDetailsViewModel = hiltViewModel(),
    onNavigationRequest: () -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.onEach {
            when (it) {
                PhotoDetailsContract.Effect.NavigateUp -> onNavigationRequest()
            }
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
            is UIState.Loading -> {
                LoadingView(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                )
            }

            is UIState.Success -> {
                PhotoDetailsScreenContent(
                    photoDetailsItem = (uiState.value as UIState.Success).photoDetails,
                    onEvent = { viewModel.setEvent(it) },
                )
            }

            is UIState.Error -> {
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
                        modifier = Modifier.padding(horizontal = 2.dp, vertical = 4.dp),
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

                    Divider(
                        thickness = 1.dp,
                        color = AppColor.DividerColor
                    )

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

                    Divider(
                        thickness = 1.dp,
                        color = AppColor.DividerColor
                    )

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

                    Divider(
                        thickness = 1.dp,
                        color = AppColor.DividerColor
                    )

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

@Preview
@Composable
private fun PhotoDetailsScreenPreview() {
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
            tags = listOf("nature", "landscape", "mountains")
        ),
        onEvent = {}
    )
}