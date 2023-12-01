package com.saiful.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.saiful.presentation.utils.BlurHashDecoder

@Composable
internal fun AsyncImageBlur(
    modifier: Modifier = Modifier,
    imageUrl: String,
    blurHash: String,
    width: Int = 4,
    height: Int = 3
) {

    val bitmap = remember {
        derivedStateOf {
            BlurHashDecoder.decode(blurHash, width, height)
        }
    }

    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        loading = {
            bitmap.value?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentScale = ContentScale.Crop,
                    contentDescription = "loading image"
                )
            }
        },
        contentDescription = "main image",
        contentScale = ContentScale.Crop,
        modifier = modifier
    )

}

@Preview
@Composable
private fun AsyncImageBlurPreview() {
    AsyncImageBlur(
        modifier = Modifier.fillMaxWidth(),
        imageUrl = "https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/photo-1682687218147-9806132dc697",
        blurHash = "L:HLk^%0s:j[_Nfkj[j[%hWCWWWV"
    )
}