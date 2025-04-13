package com.saiful.data.repository.photo

import androidx.paging.LoadState
import androidx.paging.testing.ErrorRecovery
import androidx.paging.testing.asSnapshot
import com.nhaarman.mockito_kotlin.*
import com.saiful.core.domain.Result
import com.saiful.data.model.*
import com.saiful.data.model.photo.*
import com.saiful.data.model.photo.details.*
import com.saiful.data.remote.ApiService
import com.saiful.test.unit.BaseRepositoryTest
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PhotoRepositoryImplTest : BaseRepositoryTest() {

    private val apiService: ApiService = mock()

    private val page: Int = 1
    private val pageSize: Int = 10
    private val pageId: String = "id"
    private lateinit var photoRepository: PhotoRepository
    private lateinit var photoListResponse: List<Photo>
    private lateinit var photoDetailResponse: PhotoDetails


    override fun setup() {
        photoRepository = PhotoRepositoryImpl(
            apiService = apiService,
            errorMapper = errorMapper
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

        photoDetailResponse = PhotoDetails(
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
            description = "description",
            downloads = 1,
            views = 1,
            location = ImageLocation("a", "b", "c"),
            exif = Exif(
                "FUJIFILM", "X-T20", "FUJIFILM, X-T20", "1/1500", 16.0, 300.0, 10000
            ),
            tags = listOf()
        )
    }

    override fun tearDown() {
        reset(apiService)
    }

    @Test
    fun `verify photos fetch is successful`() {
        runTest {
            whenever(
                apiService.photos(
                    page = page, pageSize = pageSize
                )
            ).thenReturn(photoListResponse)

            val res = photoRepository.photosList().asSnapshot()
            assert(res == photoListResponse)

            verify(apiService, times(1)).photos(
                page = page,
                pageSize = pageSize
            )
        }
    }

    @Test
    fun `verify photos fetch is not successful`() {
        runTest {
            whenever(
                apiService.photos(
                    page = page, pageSize = pageSize
                )
            ).thenThrow(RuntimeException("Something went wrong"))

            val res = photoRepository.photosList().asSnapshot(
                onError = { loadState ->
                    assert(loadState.refresh is LoadState.Error)
                    ErrorRecovery.RETURN_CURRENT_SNAPSHOT
                }
            )

            assert(res.isEmpty())

            verify(apiService, times(1)).photos(
                page = page,
                pageSize = pageSize
            )
        }

    }

    @Test
    fun `verify photo details fetch is successful`() {
        runTest {
            whenever(
                apiService.photoDetails(pageId)
            ).thenReturn(photoDetailResponse)

            val result = photoRepository.photoDetails(pageId)
            assert(result is Result.Success)
            assert( (result as Result.Success).data == photoDetailResponse)

            verify(apiService, times(1)).photoDetails(pageId)
        }
    }

    @Test
    fun `verify photo details fetch is not successful`() {
        runTest {
            whenever(
                apiService.photoDetails(pageId)
            ).thenThrow(RuntimeException("Something went wrong"))

            val result = photoRepository.photoDetails(pageId)
            assert(result is Result.Error)

            verify(apiService, times(1)).photoDetails(pageId)
        }
    }
}