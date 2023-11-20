package com.saiful.data.repository.photo

import androidx.paging.LoadState
import androidx.paging.testing.ErrorRecovery
import androidx.paging.testing.asSnapshot
import com.nhaarman.mockito_kotlin.*
import com.saiful.data.model.*
import com.saiful.data.model.home.*
import com.saiful.data.remote.ApiService
import com.saiful.test.unit.BaseRepositoryTest
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PhotoRepositoryImplTest : BaseRepositoryTest() {

    private val apiService: ApiService = mock()

    private val page: Int = 1
    private val pageSize: Int = 10
    private lateinit var photoRepository: PhotoRepository
    private lateinit var response: List<Photo>


    override fun setup() {
        photoRepository = PhotoRepositoryImpl(
            apiService = apiService
        )

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
                    page = page, pageSize = pageSize
                )
            ).thenReturn(response)

            val res = photoRepository.photosList().asSnapshot()
            assert(res == response)

            verify(apiService, times(1)).photos(
                page = page,
                pageSize = pageSize
            )
        }
    }

    @Test
    fun `verify home photos fetch is not successful`() {
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
}