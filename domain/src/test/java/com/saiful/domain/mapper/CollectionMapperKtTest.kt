package com.saiful.domain.mapper

import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.testing.asSnapshot
import com.saiful.data.model.Links
import com.saiful.data.model.ProfileImage
import com.saiful.data.model.Social
import com.saiful.data.model.User
import com.saiful.data.model.collection.Collection
import com.saiful.data.model.collection.CollectionLinks
import com.saiful.data.model.photo.ContentLink
import com.saiful.data.model.photo.Photo
import com.saiful.data.model.photo.Urls
import com.saiful.domain.model.CollectionItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CollectionMapperKtTest {
    private val user = User(
        acceptedTos = false,
        bio = null,
        instagramUsername = null,
        firstName = "name",
        lastName = null,
        forHire = false,
        id = "12",
        location = null,
        name = "name",
        portfolioUrl = null,
        links = Links(
            followers = "",
            following = "",
            html = "",
            likes = "",
            photos = "",
            portfolio = "",
            self = ""
        ),
        username = "abc",
        profileImage = ProfileImage(
            small = "url", medium = "url", large = "url"
        ),
        social = Social(
            instagramUsername = "username",
            portfolioUrl = "username",
            twitterUsername = null
        ),
        totalCollections = 0,
        totalLikes = 0,
        totalPhotos = 1,
        twitterUsername = null,
        updatedAt = ""
    )

    private val photo = Photo(
        id = "1",
        altDescription = "",
        blurHash = "",
        color = "",
        height = 11316,
        width = 7125,
        createdAt = "",
        currentUserCollections = emptyList(),
        likedByUser = false,
        likes = 100,
        links = ContentLink(
            download = "", downloadLocation = "", html = "", self = ""
        ),
        promotedAt = "",
        slug = "",
        sponsorship = null,
        updatedAt = "",
        urls = Urls(
            full = "url", raw = "url", regular = "url", small = "url", thumb = "url"
        ),
        user = user,
        description = "description"
    )
    private val collections: Flow<PagingData<Collection>> = flowOf(
        PagingData.from(
            listOf(
                Collection(
                    id = "1",
                    coverPhoto = photo,
                    description = "",
                    featured = false,
                    lastCollectedAt = "",
                    links = CollectionLinks(
                        html = "",
                        photos = "",
                        related = "",
                        self = ""
                    ),
                    previewPhotos = emptyList(),
                    private = false,
                    publishedAt = "",
                    shareKey = "",
                    tags = emptyList(),
                    title = "title",
                    totalPhotos = 124,
                    updatedAt = "",
                    user = user
                )
            )
        )
    )

    private val expectedList = collections.map { pagingData ->
        pagingData.map {
            CollectionItem(
                collectionId = it.id,
                profileImage = it.user.profileImage.small,
                profileName = it.user.name,
                mainImage = it.coverPhoto.urls.regular,
                mainImageBlurHash = "",
                mainImageHeight = ((it.coverPhoto.height.toDouble() / it.coverPhoto.width.toDouble()) * 10).toInt(),
                mainImageWidth = (it.coverPhoto.width / it.coverPhoto.width) * 10,
                title = it.title,
                totalPhoto = it.totalPhotos,
                description = it.description ?: "",
            )
        }
    }


    @Test
    fun `verify toCollectionItem() maps properly`() {
        runTest {
            assert(collections.toCollectionItem().asSnapshot() == expectedList.asSnapshot())
        }
    }
}