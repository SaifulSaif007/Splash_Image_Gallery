package com.saiful.presentation.composables

import androidx.compose.foundation.clickable
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
import com.saiful.domain.model.PhotoItem
import com.saiful.domain.usecase.photoId
import com.saiful.presentation.R
import com.saiful.presentation.theme.primaryText
import com.saiful.presentation.theme.titleText
import com.saiful.presentation.utils.TestTags
import com.saiful.presentation.utils.TestTags.PROFILE_IMAGE
import com.saiful.presentation.utils.TestTags.PROFILE_NAME
import com.saiful.presentation.utils.TestTags.SPONSOR_LABEL

@Composable
internal fun PhotoRowItem(
    modifier: Modifier = Modifier,
    photoItem: PhotoItem,
    onItemClick: (photoId: photoId) -> Unit
) {
    Column(
        modifier.padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photoItem.profileImage)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.ic_profile),
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
                    text = photoItem.profileName,
                    style = MaterialTheme.typography.titleText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(PROFILE_NAME)
                )

                if (photoItem.sponsored) {
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
            Box(modifier = Modifier.clip(RoundedCornerShape(12.dp))) {
                AsyncImageBlur(
                    imageUrl = photoItem.mainImage,
                    blurHash = photoItem.mainImageBlurHash,
                    height = photoItem.mainImageHeight,
                    width = photoItem.mainImageWidth,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            onItemClick(photoItem.photoId)
                        }
                        .testTag(TestTags.MAIN_IMAGE)
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun PhotoRowItemPreview() {
    PhotoRowItem(
        photoItem = PhotoItem(
            photoId = "1",
            profileImage = "https://images.unsplash.com/profile-1679489218992-ebe823c797dfimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32",
            profileName = "NEOM",
            sponsored = true,
            mainImage = "https://images.unsplash.com/photo-1682905926517-6be3768e29f0?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3wxNzQ1NDV8MXwxfGFsbHwxfHx8fHx8Mnx8MTY5NTU3Mzk2OXw&ixlib=rb-4.0.3&q=80&w=200",
            mainImageBlurHash = "L:HLk^%0s:j[_Nfkj[j[%hWCWWWV",
            mainImageWidth = 4,
            mainImageHeight = 3
        ),
        onItemClick = {}
    )
}