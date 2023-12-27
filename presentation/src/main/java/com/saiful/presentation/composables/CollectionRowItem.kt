package com.saiful.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.saiful.domain.model.CollectionItem
import com.saiful.domain.usecase.collectionId
import com.saiful.presentation.R
import com.saiful.presentation.theme.AppColor
import com.saiful.presentation.theme.collectionSubtitle
import com.saiful.presentation.theme.collectionTitle
import com.saiful.presentation.theme.titleText
import com.saiful.presentation.utils.TestTags
import com.saiful.presentation.utils.TestTags.COLLECTION_TITLE
import com.saiful.presentation.utils.TestTags.COLLECTION_TOTAL_PHOTOS
import com.saiful.presentation.utils.TestTags.MAIN_IMAGE

@Composable
internal fun CollectionRowItem(
    modifier: Modifier = Modifier,
    collectionItem: CollectionItem,
    onItemClick: (collectionId) -> Unit = {}
) {
    Column(
        modifier.padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(collectionItem.profileImage)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.ic_profile),
                contentDescription = "icon",
                modifier = Modifier
                    .size(height = 45.dp, width = 45.dp)
                    .clip(CircleShape)
                    .testTag(TestTags.PROFILE_IMAGE),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = collectionItem.profileName,
                    style = MaterialTheme.typography.titleText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(TestTags.PROFILE_NAME)
                )

            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row {
            Box(modifier = Modifier.clip(RoundedCornerShape(12.dp))) {
                AsyncImageBlur(
                    imageUrl = collectionItem.mainImage,
                    blurHash = collectionItem.mainImageBlurHash,
                    height = collectionItem.mainImageHeight,
                    width = collectionItem.mainImageWidth,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            onItemClick(collectionItem.collectionId)
                        }
                        .testTag(MAIN_IMAGE)
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .background(
                            Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0.0f to Color.Transparent,
                                    0.6f to AppColor.GradientBlack
                                )
                            )
                        )
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = collectionItem.title,
                        style = MaterialTheme.typography.collectionTitle,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(COLLECTION_TITLE)
                    )
                    Text(
                        text = stringResource(
                            id = R.string.total_photos,
                            collectionItem.totalPhoto
                        ),
                        style = MaterialTheme.typography.collectionSubtitle,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(COLLECTION_TOTAL_PHOTOS)
                    )
                }

            }

        }
    }
}

@Preview
@Composable
private fun CollectionRowItemPreview() {
    CollectionRowItem(
        collectionItem =
        CollectionItem(
            collectionId = "1",
            profileImage = "https://images.unsplash.com/profile-1679489218992-ebe823c797dfimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32",
            profileName = "NEOM",
            mainImage = "https://images.unsplash.com/photo-1682905926517-6be3768e29f0?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3wxNzQ1NDV8MXwxfGFsbHwxfHx8fHx8Mnx8MTY5NTU3Mzk2OXw&ixlib=rb-4.0.3&q=80&w=200",
            mainImageBlurHash = "L:HLk^%0s:j[_Nfkj[j[%hWCWWWV",
            mainImageWidth = 6,
            mainImageHeight = 3,
            title = "Sad",
            totalPhoto = 127
        )
    )
}