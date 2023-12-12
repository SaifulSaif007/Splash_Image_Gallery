package com.saiful.presentation.composables

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.saiful.domain.model.PhotoItem
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
    private lateinit var photoItem: PhotoItem

    @Test
    fun `verify PhotoRowItem works properly when sponsored is true`() {
        photoItem = PhotoItem(
            profileImage = "",
            profileName = "NEOM",
            sponsored = true,
            mainImage = "",
            mainImageBlurHash = "",
            mainImageHeight = 4,
            mainImageWidth = 3
        )

        with(rule) {
            setContent {
                PhotoRowItem(modifier = Modifier, photoItem = photoItem, onItemClick = {})
            }

            onNodeWithTag(PROFILE_NAME).apply {
                assertIsDisplayed()
                assertTextEquals(photoItem.profileName)
            }

            onNodeWithTag(SPONSOR_LABEL).assertIsDisplayed()

            onNodeWithTag(PROFILE_IMAGE).assertIsDisplayed()

            onNodeWithTag(MAIN_IMAGE).assertIsDisplayed().performClick()

        }
    }

    @Test
    fun `verify PhotoRowItem works properly when sponsored is false`() {
        photoItem = PhotoItem(
            profileImage = "",
            profileName = "NEOM",
            sponsored = false,
            mainImage = "",
            mainImageBlurHash = "",
            mainImageHeight = 4,
            mainImageWidth = 3
        )

        with(rule) {
            setContent {
                PhotoRowItem(modifier = Modifier, photoItem = photoItem, onItemClick = {})
            }

            onNodeWithTag(PROFILE_NAME).apply {
                assertIsDisplayed()
                assertTextEquals(photoItem.profileName)
            }

            onNodeWithTag(SPONSOR_LABEL).assertDoesNotExist()

            onNodeWithTag(PROFILE_IMAGE).assertIsDisplayed()

            onNodeWithTag(MAIN_IMAGE).assertIsDisplayed().performClick()

        }
    }
}