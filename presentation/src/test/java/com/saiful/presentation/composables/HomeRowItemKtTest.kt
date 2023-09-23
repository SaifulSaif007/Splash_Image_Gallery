package com.saiful.presentation.composables

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.saiful.presentation.utils.TestTags
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(instrumentedPackages = ["androidx.loader.content"])
class HomeRowItemKtTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun `basicTest`() {
        with(rule) {
            setContent {
                HomeRowItem(modifier = Modifier)
            }

            onNodeWithTag(TestTags.PROFILE_NAME).assertIsDisplayed()
        }
    }
}