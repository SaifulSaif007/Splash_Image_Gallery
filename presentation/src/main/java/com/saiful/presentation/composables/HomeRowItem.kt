package com.saiful.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.saiful.domain.model.HomeItem
import com.saiful.presentation.R
import com.saiful.presentation.theme.primaryText
import com.saiful.presentation.theme.titleText
import com.saiful.presentation.utils.TestTags.MAIN_IMAGE
import com.saiful.presentation.utils.TestTags.PROFILE_IMAGE
import com.saiful.presentation.utils.TestTags.PROFILE_NAME
import com.saiful.presentation.utils.TestTags.SPONSOR_LABEL

@Composable
internal fun HomeRowItem(
    modifier: Modifier,
    homeItem: HomeItem
) {
    Column(
        modifier.padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(homeItem.profileImage)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "icon",
                modifier = Modifier
                    .size(height = 45.dp, width = 45.dp)
                    .clip(CircleShape)
                    .testTag(PROFILE_IMAGE),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {

                Text(
                    text = homeItem.profileName,
                    style = MaterialTheme.typography.titleText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(PROFILE_NAME)
                )

                if (homeItem.sponsored) {
                    Text(
                        text = "Sponsored",
                        style = MaterialTheme.typography.primaryText,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(SPONSOR_LABEL)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(homeItem.mainImage)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .testTag(MAIN_IMAGE)
            )
        }
    }

}

@Preview
@Composable
private fun HomeRowItemPreview() {
    HomeRowItem(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        homeItem = HomeItem(
            profileImage = "https://images.unsplash.com/profile-1679489218992-ebe823c797dfimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32",
            profileName = "NEOM",
            sponsored = true,
            mainImage = "https://images.unsplash.com/photo-1682905926517-6be3768e29f0?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3wxNzQ1NDV8MXwxfGFsbHwxfHx8fHx8Mnx8MTY5NTU3Mzk2OXw&ixlib=rb-4.0.3&q=80&w=200",
        )
    )
}