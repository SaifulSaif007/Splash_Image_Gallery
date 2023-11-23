package com.saiful.presentation.composables

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
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
                profileImage = "",
                profileName = "NEOM",
                mainImage = "",
                title = "Sad",
                totalPhoto = 124
            )


            setContent {
                CollectionRowItem(collectionItem = collectionItem)
                totalPhotosPrefix = stringResource(id = R.string.total_photos, collectionItem.totalPhoto)
            }
            onNodeWithTag(TestTags.PROFILE_NAME).apply {
                assertIsDisplayed()
                assertTextEquals(collectionItem.profileName)
            }

            onNodeWithTag(TestTags.PROFILE_IMAGE).assertIsDisplayed()

            onNodeWithTag(TestTags.MAIN_IMAGE).assertIsDisplayed()

            onNodeWithTag(TestTags.COLLECTION_TITLE).apply {
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