package com.saiful.data.repository.profile

import androidx.paging.LoadState
import androidx.paging.testing.ErrorRecovery
import androidx.paging.testing.asSnapshot
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.saiful.core.domain.Result
import com.saiful.data.model.*
import com.saiful.data.model.collection.*
import com.saiful.data.model.collection.Collection
import com.saiful.data.model.photo.*
import com.saiful.data.model.profile.Profile
import com.saiful.data.remote.ApiService
import com.saiful.test.unit.BaseRepositoryTest
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ProfileRepositoryImplTest : BaseRepositoryTest() {

    private val apiService: ApiService = mock()

    private lateinit var profileRepository: ProfileRepository
    private lateinit var profileResponse: Profile
    private val userName = "saiful"
    private val page: Int = 1
    private val pageSize: Int = 10
    private lateinit var photoListResponse: List<Photo>
    private lateinit var collectionResponse: List<Collection>

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

        photoListResponse = listOf(
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

        val user = User(
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

        collectionResponse = listOf(
            Collection(
                id = "1",
                coverPhoto = Photo(
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
                ),
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
                title = "title",
                totalPhotos = 124,
                updatedAt = "",
                user = user
            )
        )
    }

    override fun tearDown() {
        reset(apiService)
    }


    @Test
    fun `verify get profile success`() {
        runTest {
            whenever(
                apiService.profile(userName)
            ).thenReturn(profileResponse)

            val result = profileRepository.profile(userName)
            assert(result is Result.Success)
            verify(apiService, times(1)).profile(userName)
        }
    }

    @Test
    fun `verify get profile failure`() {
        runTest {
            whenever(
                apiService.profile(userName)
            ).thenThrow(RuntimeException())

            val result = profileRepository.profile(userName)
            assert(result is Result.Error)
            verify(apiService, times(1)).profile(userName)
        }
    }

    @Test
    fun `verify get profile photos success`() {
        runTest {
            whenever(
                apiService.profilePhotos(userName, page, pageSize)
            ).thenReturn(photoListResponse)

            val result = profileRepository.profilePhotos(userName).asSnapshot()

            assert(result == photoListResponse)
            verify(apiService, times(1)).profilePhotos(userName, page, pageSize)
        }
    }

    @Test
    fun `verify get profile photos is not successful`() {
        runTest {
            whenever(
                apiService.profilePhotos(userName, page, pageSize)
            ).thenThrow(RuntimeException())

            val result = profileRepository.profilePhotos(userName).asSnapshot(
                onError = { loadState ->
                    assert(loadState.refresh is LoadState.Error)
                    ErrorRecovery.RETURN_CURRENT_SNAPSHOT
                }
            )

            assert(result.isEmpty())
            verify(apiService, times(1)).profilePhotos(userName, page, pageSize)
        }
    }

    @Test
    fun `verify get profile liked photos success`() {
        runTest {
            whenever(
                apiService.profileLikedPhotos(userName, page, pageSize)
            ).thenReturn(photoListResponse)

            val result = profileRepository.profileLikedPhotos(userName).asSnapshot()

            assert(result == photoListResponse)
            verify(apiService, times(1)).profileLikedPhotos(userName, page, pageSize)
        }
    }

    @Test
    fun `verify get profile liked photos is not successful`() {
        runTest {
            whenever(
                apiService.profileLikedPhotos(userName, page, pageSize)
            ).thenThrow(RuntimeException())

            val result = profileRepository.profileLikedPhotos(userName).asSnapshot(
                onError = { loadState ->
                    assert(loadState.refresh is LoadState.Error)
                    ErrorRecovery.RETURN_CURRENT_SNAPSHOT
                }
            )

            assert(result.isEmpty())
            verify(apiService, times(1)).profileLikedPhotos(userName, page, pageSize)
        }
    }

    @Test
    fun `verify get profile collections success`() {
        runTest {
            whenever(
                apiService.profileCollections(userName, page, pageSize)
            ).thenReturn(collectionResponse)

            val result = profileRepository.profilePhotoCollections(userName).asSnapshot()

            assert(result == collectionResponse)
            verify(apiService, times(1)).profileCollections(userName, page, pageSize)
        }
    }

    @Test
    fun `verify get profile collections is not successful`() {
        runTest {
            whenever(
                apiService.profileCollections(userName, page, pageSize)
            ).thenThrow(RuntimeException())

            val result = profileRepository.profilePhotoCollections(userName).asSnapshot(
                onError = { loadState ->
                    assert(loadState.refresh is LoadState.Error)
                    ErrorRecovery.RETURN_CURRENT_SNAPSHOT
                }
            )

            assert(result.isEmpty())
            verify(apiService, times(1)).profileCollections(userName, page, pageSize)
        }
    }
}