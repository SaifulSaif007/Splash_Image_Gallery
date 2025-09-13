package com.saiful.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.saiful.domain.model.SearchUserItem
import com.saiful.presentation.R
import com.saiful.presentation.utils.TestTags
import com.saiful.presentation.utils.TestTags.PROFILE_IMAGE

@Composable
fun SearchUserRowItem(
    modifier: Modifier = Modifier,
    user: SearchUserItem
) {
    Column(modifier = modifier.padding(vertical = 6.dp)) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    //TODO
                }
                .testTag(TestTags.PROFILE_ROW)

        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.profileImage)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.ic_profile),
                contentDescription = "icon",
                modifier = Modifier
                    .padding(4.dp)
                    .size(height = 45.dp, width = 45.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(horizontal = 12.dp),
            ) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Text(
                    text = user.userName,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    user.photos.forEach { url ->
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(url)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "icon",
                            modifier = Modifier
                                .size(height = 100.dp, width = 90.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .testTag(PROFILE_IMAGE),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
private fun SearchUserRowItemPreview() {
    SearchUserRowItem(
        user = SearchUserItem(
            userId = "1",
            userName = "saif@saif",
            name = "Saif",
            profileImage = "",
            photos = listOf(
                "url1",
                "url2",
                "url3"
            )
        )
    )
}