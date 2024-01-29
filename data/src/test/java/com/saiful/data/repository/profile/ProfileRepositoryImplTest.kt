package com.saiful.data.repository.profile

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.saiful.core.domain.Result
import com.saiful.data.model.Links
import com.saiful.data.model.ProfileImage
import com.saiful.data.model.Social
import com.saiful.data.model.collection.PreviewPhoto
import com.saiful.data.model.photo.Urls
import com.saiful.data.model.profile.Profile
import com.saiful.data.remote.ApiService
import com.saiful.test.unit.BaseRepositoryTest
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ProfileRepositoryImplTest : BaseRepositoryTest() {

    private val apiService: ApiService = mock()

    private lateinit var profileRepository: ProfileRepository
    private lateinit var profileResponse: Profile

    override fun setup() {
        profileRepository = ProfileRepositoryImpl(apiService, errorMapper)

        profileResponse = Profile(
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
                following = "https://api.unsplash.com/users/john-doe/following",
                followers = "https://api.unsplash.com/users/john-doe/followers",
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
            followersCount = 1000,
            followingCount = 500,
            downloads = 10000
        )
    }

    override fun tearDown() {
        reset(apiService)
    }


    @Test
    fun `verify get profile success`() {
        runTest {
            whenever(
                apiService.profile("username")
            ).thenReturn(profileResponse)

            val result = profileRepository.profile("username")
            assert(result is Result.Success)
            verify(apiService, times(1)).profile("username")
        }
    }

    @Test
    fun `verify get profile failure`() {
        runTest {
            whenever(
                apiService.profile("username")
            ).thenThrow(RuntimeException())

            val result = profileRepository.profile("username")
            assert(result is Result.Error)
            verify(apiService, times(1)).profile("username")
        }
    }
}