package com.saiful.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saiful.presentation.R
import com.saiful.presentation.theme.SplashGalleryTheme
import com.saiful.presentation.utils.TestTags

@Composable
internal fun EmptyView(
    modifier: Modifier = Modifier,
    msg: String = "No data found"
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.empty),
            contentDescription = "empty icon",
            modifier = Modifier
                .padding(bottom = 20.dp)
                .testTag(TestTags.EMPTY_ICON)
        )

        Text(
            modifier = Modifier
                .padding(bottom = 8.dp, top = 8.dp)
                .testTag(TestTags.EMPTY_TEXT),
            text = msg,
            style = MaterialTheme.typography.bodyMedium,
        )


    }
}

@Preview(showBackground = true)
@Composable
fun EmptyViewPreview() {
    SplashGalleryTheme {
        EmptyView(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp)
        )
    }
}