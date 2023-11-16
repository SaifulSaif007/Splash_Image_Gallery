package com.saiful.presentation.composables

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.saiful.presentation.utils.TestTags.CIRCULAR_INDICATOR
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(instrumentedPackages = ["androidx.loader.content"])
class LoadingViewKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun `verify LoadingView shows properly`() {
        with(rule) {
            setContent {
                LoadingView()
            }

            onNodeWithTag(CIRCULAR_INDICATOR).assertIsDisplayed()
        }
    }
}