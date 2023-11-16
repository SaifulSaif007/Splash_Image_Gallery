package com.saiful.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saiful.presentation.theme.errorText
import com.saiful.presentation.utils.TestTags.ERROR_ACTION_BUTTON
import com.saiful.presentation.utils.TestTags.ERROR_TEXT

@Composable
internal fun ErrorView(
    modifier: Modifier,
    errorMsg: String = "Something went wrong",
    onActionButtonText: String = "Try Again",
    onAction: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .testTag(ERROR_TEXT),
            text = errorMsg,
            style = MaterialTheme.typography.errorText
        )

        Button(
            onClick = onAction,
            modifier = Modifier.testTag(ERROR_ACTION_BUTTON)
        ) {
            Text(text = onActionButtonText)
        }
    }
}


@Preview(showBackground = false)
@Composable
private fun ErrorViewPreview() {
    ErrorView(
        modifier = Modifier.fillMaxSize(),
        errorMsg = "Something went wrong",
        onAction = {}
    )
}