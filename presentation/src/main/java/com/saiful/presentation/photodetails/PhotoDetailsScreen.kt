package com.saiful.presentation.photodetails

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
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
import com.saiful.presentation.theme.titleText
import com.saiful.presentation.utils.TestTags

@Composable
internal fun PhotoDetailsScreen(
    viewModel: PhotoDetailsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    when (uiState.value) {
        is UIState.Loading -> {
            LoadingView(
                modifier = Modifier.fillMaxSize()
            )
        }

        is UIState.Success -> {
            PhotoDetailsScreenContent(
                photoDetailsItem = (uiState.value as UIState.Success).photoDetails
            )
        }

        is UIState.Error -> {
            ErrorView(
                modifier = Modifier.fillMaxSize(),
                onAction = {
                    //viewModel.getPhotoDetails()
                }
            )
        }
    }
}

@Composable
private fun PhotoDetailsScreenContent(photoDetailsItem: PhotoDetailsItem) {
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
                    modifier = Modifier.padding(2.dp),
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
                        Text(text = "Camera")
                        Text(text = photoDetailsItem.camera)
                        Text(text = "Focal Length")
                        Text(text = photoDetailsItem.focalLength)
                        Text(text = "ISO")
                        Text(text = photoDetailsItem.iso)
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 6.dp)
                    ) {
                        Text(text = "Aperture")
                        Text(text = photoDetailsItem.aperture)
                        Text(text = "Shutter Speed")
                        Text(text = photoDetailsItem.shutterSpeed)
                        Text(text = "Dimensions")
                        Text(text = photoDetailsItem.dimensions)
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
                        Text(text = "Views")
                        Text(text = photoDetailsItem.views)
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Downloads")
                        Text(text = photoDetailsItem.downloads)
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Likes")
                        Text(text = photoDetailsItem.likes)
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
                    SuggestionChip(
                        onClick = { },
                        label = { Text(text = "diving") }
                    )

                    SuggestionChip(
                        onClick = { },
                        label = { Text(text = "ocean") }
                    )

                    SuggestionChip(
                        onClick = { },
                        label = { Text(text = "swimmer") }
                    )

                    SuggestionChip(
                        onClick = { },
                        label = { Text(text = "reef") }
                    )

                    SuggestionChip(
                        onClick = { },
                        label = { Text(text = "underwater") }
                    )
                }
            }
        }
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
        )
    )
}