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
import com.saiful.presentation.R
import com.saiful.presentation.theme.primaryText
import com.saiful.presentation.utils.TestTags.MAIN_IMAGE
import com.saiful.presentation.utils.TestTags.PROFILE_IMAGE
import com.saiful.presentation.utils.TestTags.PROFILE_NAME
import com.saiful.presentation.utils.TestTags.SPONSOR_LABEL


@Composable
fun HomeRowItem(
    modifier: Modifier
) {
    Column(
        modifier.padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://example.com/image.jpg")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(height = 45.dp, width = 45.dp)
                    .clip(CircleShape)
                    .testTag(PROFILE_IMAGE)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {

                Text(
                    text = "NEOM",
                    style = MaterialTheme.typography.primaryText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(PROFILE_NAME)
                )

                Text(
                    text = "Sponsored",
                    style = MaterialTheme.typography.primaryText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(SPONSOR_LABEL)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://example.com/image.jpg")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "icon",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .testTag(MAIN_IMAGE)
            )
        }
    }

}

@Preview
@Composable
fun HomeRowItemPreview() {
    HomeRowItem(modifier = Modifier.background(MaterialTheme.colorScheme.background))
}