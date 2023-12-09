package com.saiful.presentation.photodetails

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.saiful.presentation.R
import com.saiful.presentation.composables.AsyncImageBlur
import com.saiful.presentation.theme.AppColor
import com.saiful.presentation.theme.titleText
import com.saiful.presentation.utils.TestTags

@Composable
internal fun PhotoDetailsScreen() {
    PhotoDetailsScreenContent()
}

@Composable
private fun PhotoDetailsScreenContent() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            AsyncImageBlur(
                imageUrl = "https://images.unsplash.com/photo-1682905926517-6be3768e29f0?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3wxNzQ1NDV8MXwxfGFsbHwxfHx8fHx8Mnx8MTY5NTU3Mzk2OXw&ixlib=rb-4.0.3&q=80&w=200",
                blurHash = "L:HLk^%0s:j[_Nfkj[j[%hWCWWWV",
                modifier = Modifier.fillMaxWidth()
            )

            Column(
                modifier = Modifier.padding(6.dp)
            ) {

                Row(
                    modifier = Modifier.padding(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("")
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(id = R.drawable.ic_profile),
                        contentDescription = "icon",
                        modifier = Modifier
                            .size(height = 40.dp, width = 40.dp)
                            .clip(CircleShape)
                            .testTag(TestTags.PROFILE_IMAGE),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "NEOM",
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
                        Text(text = "Unknown")
                        Text(text = "Focal Length")
                        Text(text = "0.0mm")
                        Text(text = "ISO")
                        Text(text = "Unknown")
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 6.dp)
                    ) {
                        Text(text = "Aperture")
                        Text(text = "Unknown")
                        Text(text = "Shutter Speed")
                        Text(text = "Unknown")
                        Text(text = "Dimensions")
                        Text(text = "5400 x 3600")
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
                        Text(text = "4.0M")
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Downloads")
                        Text(text = "36.1k")
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Likes")
                        Text(text = "297")
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
fun PhotoDetailsScreenPreview() {
    PhotoDetailsScreenContent()
}