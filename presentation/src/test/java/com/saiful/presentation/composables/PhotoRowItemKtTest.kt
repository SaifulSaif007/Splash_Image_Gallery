package com.saiful.presentation.composables

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.saiful.domain.model.HomeItem
import com.saiful.presentation.utils.TestTags.MAIN_IMAGE
import com.saiful.presentation.utils.TestTags.PROFILE_IMAGE
import com.saiful.presentation.utils.TestTags.PROFILE_NAME
import com.saiful.presentation.utils.TestTags.SPONSOR_LABEL
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(instrumentedPackages = ["androidx.loader.content"])
class PhotoRowItemKtTest {

    @get:Rule
    val rule = createComposeRule()
    private lateinit var homeItem: HomeItem

    @Test
    fun `verify PhotoRowItem works properly when sponsored is true`() {
        homeItem = HomeItem(
            profileImage = "",
            profileName = "NEOM",
            sponsored = true,
            mainImage = ""
        )

        with(rule) {
            setContent {
                PhotoRowItem(modifier = Modifier, homeItem = homeItem)
            }

            onNodeWithTag(PROFILE_NAME).apply {
                assertIsDisplayed()
                assertTextEquals(homeItem.profileName)
            }

            onNodeWithTag(SPONSOR_LABEL).assertIsDisplayed()

            onNodeWithTag(PROFILE_IMAGE).assertIsDisplayed()

            onNodeWithTag(MAIN_IMAGE).assertIsDisplayed()

        }
    }

    @Test
    fun `verify PhotoRowItem works properly when sponsored is false`() {
        homeItem = HomeItem(
            profileImage = "",
            profileName = "NEOM",
            sponsored = false,
            mainImage = ""
        )

        with(rule) {
            setContent {
                PhotoRowItem(modifier = Modifier, homeItem = homeItem)
            }

            onNodeWithTag(PROFILE_NAME).apply {
                assertIsDisplayed()
                assertTextEquals(homeItem.profileName)
            }

            onNodeWithTag(SPONSOR_LABEL).assertDoesNotExist()

            onNodeWithTag(PROFILE_IMAGE).assertIsDisplayed()

            onNodeWithTag(MAIN_IMAGE).assertIsDisplayed()

        }
    }
}