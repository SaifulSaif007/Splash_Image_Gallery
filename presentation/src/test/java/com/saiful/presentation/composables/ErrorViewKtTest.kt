package com.saiful.presentation.composables

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.saiful.presentation.utils.TestTags.ERROR_ACTION_BUTTON
import com.saiful.presentation.utils.TestTags.ERROR_TEXT
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(instrumentedPackages = ["androidx.loader.content"])
class ErrorViewKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun `verify ErrorView shows properly`() {
        val errorMsg = "Something went wrong"
        val actionBtnText = "try again"
        with(rule) {
            setContent {
                ErrorView(
                    modifier = Modifier,
                    errorMsg = errorMsg,
                    onActionButtonText = actionBtnText,
                    onAction = {}
                )
            }

            onNodeWithTag(ERROR_TEXT).apply {
                assertIsDisplayed()
                assertTextEquals(errorMsg)
            }

            onNodeWithTag(ERROR_ACTION_BUTTON).apply {
                assertIsDisplayed()
                assertTextEquals(actionBtnText)
                performClick()
            }
        }
    }
}