package com.saiful.presentation.composables

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.saiful.presentation.utils.TestTags.EMPTY_ICON
import com.saiful.presentation.utils.TestTags.EMPTY_TEXT
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(instrumentedPackages = ["androidx.loader.content"])
class EmptyViewKtTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun `verify EmptyView shows properly`() {
        val msg = "No data found"
        with(rule) {
            setContent {
                EmptyView(msg = msg)
            }

            onNodeWithTag(EMPTY_ICON).apply {
                assertIsDisplayed()
            }

            onNodeWithTag(EMPTY_TEXT).apply {
                assertIsDisplayed()
                assertTextEquals(msg)
            }
        }
    }

}