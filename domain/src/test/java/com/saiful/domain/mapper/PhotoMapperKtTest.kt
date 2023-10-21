package com.saiful.domain.mapper

import com.saiful.data.model.home.*
import com.saiful.domain.model.HomeItem
import org.junit.Test

class PhotoMapperKtTest {

    private val photos: List<Photo> = listOf(
        Photo(
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
            user = User(
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
            ),
            description = "description"
        )
    )

    private val expectedList = photos.map {
        HomeItem(
            profileImage = it.user.profileImage.small,
            profileName = it.user.name,
            sponsored = it.sponsorship != null,
            mainImage = it.urls.small
        )
    }

    @Test
    fun `verify toHomeItem() maps properly`() {
        assert(photos.toHomeItem() == expectedList)
    }
}