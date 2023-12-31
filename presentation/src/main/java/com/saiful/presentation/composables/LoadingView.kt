package com.saiful.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saiful.presentation.utils.TestTags.CIRCULAR_INDICATOR

@Composable
internal fun LoadingView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        CircularProgressIndicator(
            modifier = Modifier
                .padding(12.dp)
                .testTag(CIRCULAR_INDICATOR),
            strokeCap = StrokeCap.Square
        )
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    LoadingView(
        modifier = Modifier.fillMaxSize()
    )
}