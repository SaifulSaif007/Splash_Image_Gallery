package com.saiful.data.repository

import androidx.paging.LoadState
import androidx.paging.testing.ErrorRecovery
import androidx.paging.testing.asSnapshot
import com.nhaarman.mockito_kotlin.*
import com.saiful.data.model.home.*
import com.saiful.data.model.param.HomeParams
import com.saiful.data.remote.ApiService
import com.saiful.test.unit.BaseRepositoryTest
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PhotoRepositoryImplTest : BaseRepositoryTest() {

    private val apiService: ApiService = mock()

    private lateinit var photoRepository: PhotoRepository
    private lateinit var homeParams: HomeParams
    private lateinit var response: List<Photo>

    override fun setup() {
        photoRepository = PhotoRepositoryImpl(
            apiService = apiService
        )

        homeParams = HomeParams(page = 1, pageSize = 10)

        response = listOf(
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

    }

    override fun tearDown() {
        reset(apiService)
    }

    @Test
    fun `verify home photos fetch is successful`() {
        runTest {
            whenever(
                apiService.photos(
                    page = homeParams.page, pageSize = homeParams.pageSize
                )
            ).thenReturn(response)

            val res = photoRepository.photosList().asSnapshot()
            assert(res == response)

            verify(apiService, times(1)).photos(
                page = homeParams.page,
                pageSize = homeParams.pageSize
            )
        }
    }

    @Test
    fun `verify home photos fetch is not successful`() {
        runTest {
            whenever(
                apiService.photos(
                    page = homeParams.page, pageSize = homeParams.pageSize
                )
            ).thenThrow(RuntimeException("Something went wrong"))

            val photos = photoRepository.photosList().asSnapshot(
                onError = { loadState ->
                    assert( loadState.refresh is LoadState.Error)
                    ErrorRecovery.RETURN_CURRENT_SNAPSHOT
                }
            )

            assert(photos.isEmpty())

            verify(apiService, times(1)).photos(
                page = homeParams.page,
                pageSize = homeParams.pageSize
            )
        }

    }
}