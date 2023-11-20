package com.saiful.domain.mapper

import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.testing.asSnapshot
import com.saiful.data.model.*
import com.saiful.data.model.collection.Collection
import com.saiful.data.model.collection.CollectionLinks
import com.saiful.data.model.home.*
import com.saiful.domain.model.CollectionItem
import kotlinx.coroutines.flow.*
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
        height = 100,
        width = 100,
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
                profileImage = it.user.profileImage.small,
                profileName = it.user.name,
                mainImage = it.coverPhoto.urls.small,
                title = it.title,
                totalPhoto = it.totalPhotos
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