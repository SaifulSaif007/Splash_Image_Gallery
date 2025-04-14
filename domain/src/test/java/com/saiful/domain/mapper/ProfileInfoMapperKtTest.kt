package com.saiful.domain.mapper

import com.saiful.data.model.*
import com.saiful.data.model.collection.PreviewPhoto
import com.saiful.data.model.photo.Urls
import com.saiful.data.model.profile.Profile
import com.saiful.domain.model.ProfileInfo
import org.junit.Test

class ProfileInfoMapperKtTest {

    private val profile = Profile(
        acceptedTos = true,
        bio = "I am a professional photographer based in New York City.",
        firstName = "John",
        forHire = true,
        id = "1234567890",
        instagramUsername = "john_doe",
        lastName = "Doe",
        links = Links(
            html = "https://www.example.com/john-doe",
            photos = "https://api.unsplash.com/users/john-doe/photos",
            likes = "https://api.unsplash.com/users/john-doe/likes",
            portfolio = "https://www.example.com/john-doe/portfolio",
            self = "https://api.unsplash.com/users/john-doe"
        ),
        location = "New York City",
        name = "John Doe",
        portfolioUrl = "https://www.example.com/john-doe/portfolio",
        profileImage = ProfileImage(
            small = "https://example.com/small.jpg",
            medium = "https://example.com/medium.jpg",
            large = "https://example.com/large.jpg"
        ),
        social = Social(
            instagramUsername = "john_doe",
            portfolioUrl = "https://www.example.com/john-doe/portfolio",
            twitterUsername = "john_doe",
        ),
        totalCollections = 100,
        totalLikes = 1000,
        totalPhotos = 10000,
        twitterUsername = "john_doe",
        updatedAt = "2023-04-01T12:00:00Z",
        username = "john_doe",
        totalPromotedPhotos = 10,
        followedByUser = false,
        previewPhoto = listOf(
            PreviewPhoto(
                id = "1234567890",
                createdAt = "2023-04-01T12:00:00Z",
                updatedAt = "2023-04-01T12:00:00Z",
                blurHash = "some_blur_hash",
                urls = Urls(
                    raw = "https://example.com/raw.jpg",
                    full = "https://example.com/full.jpg",
                    regular = "https://example.com/regular.jpg",
                    small = "https://example.com/small.jpg",
                    thumb = "https://example.com/thumb.jpg"
                ),
                slug = "some_slug"
            )
        ),
        downloads = 10000
    )

    private val expectedProfileInfo = ProfileInfo(
        profileName = profile.name,
        profileImage = profile.profileImage.medium,
        location = profile.location.orEmpty(),
        bio = profile.bio.orEmpty(),
        photos = profile.totalPhotos.toString(),
        likes = profile.totalLikes.toString(),
        collection = profile.totalCollections.toString(),
        visibleTabs = listOf(PHOTOS, LIKES, COLLECTIONS)
    )

    @Test
    fun `map profile to profile info should return expected profile info`() {
        val actualProfileInfo = profile.toProfileInfo()
        assert(actualProfileInfo == expectedProfileInfo)
    }
}