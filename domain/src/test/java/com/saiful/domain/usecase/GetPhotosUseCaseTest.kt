package com.saiful.domain.usecase

import com.nhaarman.mockito_kotlin.*
import com.saiful.core.domain.Result
import com.saiful.data.model.home.*
import com.saiful.data.model.param.HomeParams
import com.saiful.data.repository.PhotoRepository
import com.saiful.domain.mapper.toHomeItem
import com.saiful.test.unit.BaseUseCaseTest
import com.saiful.test.unit.rules.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class GetPhotosUseCaseTest : BaseUseCaseTest() {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val photoRepository: PhotoRepository = mock()

    private val getPhotosUseCase = GetPhotosUseCase(photoRepository)

    private lateinit var homeParams: HomeParams
    private lateinit var response: List<Photo>

    override fun setup() {
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
        reset(photoRepository)
    }


    @Test
    fun `verify photos useCase return success result`() {
        runTest(mainCoroutineRule.testDispatcher) {
            whenever(
                photoRepository.photosList(homeParams)
            ).thenReturn(
                Result.Success(data = response)
            )

            val result = getPhotosUseCase(
                params = Pair(first = 1, second = 10)
            )

            assert(result is Result.Success)
            assert((result as Result.Success).data == response.toHomeItem())
            verify(photoRepository, only()).photosList(any())

        }
    }

    @Test
    fun `verify photos useCase return error result`() {
        runTest(mainCoroutineRule.testDispatcher) {
            whenever(
                photoRepository.photosList(homeParams)
            ).thenReturn(
                Result.Error(domainException)
            )

            val result = getPhotosUseCase(
                params = Pair(first = 1, second = 10)
            )

            assert(result is Result.Error)
            assert((result as Result.Error).error == domainException)
            verify(photoRepository, only()).photosList(any())
        }
    }

}