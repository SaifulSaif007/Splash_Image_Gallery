package com.saiful.domain.usecase

import androidx.paging.*
import androidx.paging.testing.ErrorRecovery
import androidx.paging.testing.asSnapshot
import com.nhaarman.mockito_kotlin.*
import com.saiful.data.model.*
import com.saiful.data.model.photo.*
import com.saiful.data.repository.photo.PhotoRepository
import com.saiful.domain.mapper.toPhotoItem
import com.saiful.test.unit.BaseUseCaseTest
import com.saiful.test.unit.rules.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPhotosUseCaseTest : BaseUseCaseTest() {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val photoRepository: PhotoRepository = mock()

    private val getPhotosUseCase = GetPhotosUseCase(photoRepository)

    private lateinit var response: Flow<PagingData<Photo>>

    override fun setup() {
        response = flowOf(
            PagingData.from(
                listOf(
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
            )
        )
    }

    override fun tearDown() {
        reset(photoRepository)
    }


    @Test
    fun `verify photos useCase return homeItem paging data`() {
        runTest {
            whenever(
                photoRepository.photosList()
            ).thenReturn(response)

            val result = getPhotosUseCase(Unit)

            assert(result.asSnapshot() == response.toPhotoItem().asSnapshot())
            verify(photoRepository, only()).photosList()
        }
    }

    @Test
    fun `verify photos useCase return empty paging data when error occurs`() {
        runTest {
            whenever(
                photoRepository.photosList()
            ).thenReturn(
                flowOf(
                    PagingData.from(
                        data = emptyList(),
                        sourceLoadStates = LoadStates(
                            refresh = LoadState.Error(Exception("ex")),
                            prepend = LoadState.NotLoading(true),
                            append = LoadState.NotLoading(true)
                        )
                    )
                )
            )

            val result = getPhotosUseCase(Unit).asSnapshot(
                onError = { loadState ->
                    assert(loadState.refresh is LoadState.Error)
                    ErrorRecovery.RETURN_CURRENT_SNAPSHOT
                }
            )
            assert(result.isEmpty())
            verify(photoRepository, only()).photosList()
        }
    }

}