package com.saiful.presentation.composables

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.saiful.domain.model.CollectionItem
import com.saiful.presentation.R
import com.saiful.presentation.utils.TestTags
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(instrumentedPackages = ["androidx.loader.content"])
class CollectionRowItemKtTest {

    @get:Rule
    val rule = createComposeRule()
    private lateinit var collectionItem: CollectionItem
    private lateinit var totalPhotosPrefix: String

    @Test
    fun `verify CollectionRowItem shows properly`() {
        with(rule) {
            collectionItem = CollectionItem(
                collectionId = "1",
                profileImage = "",
                profileName = "NEOM",
                mainImage = "",
                mainImageBlurHash = "L:HLk^%0s:j[_Nfkj[j[%hWCWWWV",
                title = "Sad",
                totalPhoto = 124,
                mainImageHeight = 4,
                mainImageWidth = 3,
                description = "desc",
                profileUserName = "saiful",
            )


            setContent {
                CollectionRowItem(
                    collectionItem = collectionItem,
                    onProfileClick = {},
                    onItemClick = { _, _, _, _, _ -> })
                totalPhotosPrefix =
                    stringResource(id = R.string.total_photos, collectionItem.totalPhoto)
            }

            onNodeWithTag(TestTags.PROFILE_NAME, useUnmergedTree = true).apply {
                assertIsDisplayed()
                assertTextEquals(collectionItem.profileName)
            }

            onNodeWithTag(TestTags.PROFILE_IMAGE, useUnmergedTree = true).assertIsDisplayed()

            onNodeWithTag(TestTags.PROFILE_ROW, useUnmergedTree = true).apply {
                assertIsDisplayed()
                assertHasClickAction()
            }

            onNodeWithTag(TestTags.MAIN_IMAGE, useUnmergedTree = true).apply {
                assertIsDisplayed()
                assertHasClickAction()
            }

            onNodeWithTag(TestTags.COLLECTION_TITLE, useUnmergedTree = true).apply {
                assertIsDisplayed()
                assertTextEquals(collectionItem.title)
            }

            onNodeWithTag(TestTags.COLLECTION_TOTAL_PHOTOS, useUnmergedTree = true).apply {
                assertIsDisplayed()
                assertTextEquals(totalPhotosPrefix)
            }

        }
    }
}