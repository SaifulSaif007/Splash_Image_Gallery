package com.saiful.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saiful.presentation.theme.errorText

@Composable
internal fun ErrorView(
    modifier: Modifier,
    errorMsg: String = "Something went wrong",
    onAction: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = errorMsg,
            style = MaterialTheme.typography.errorText
        )

        Button(onClick = onAction) {
            Text(text = "Try Again")
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