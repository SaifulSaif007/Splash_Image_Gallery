package com.saiful.domain.usecase

import androidx.paging.*
import androidx.paging.testing.ErrorRecovery
import androidx.paging.testing.asSnapshot
import com.saiful.data.model.*
import com.saiful.data.model.photo.*
import com.saiful.data.repository.profile.ProfileRepository
import com.saiful.domain.mapper.toPhotoItem
import com.saiful.test.unit.BaseUseCaseTest
import io.mockk.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetProfileLikedPhotosUseCaseTest : BaseUseCaseTest() {
    private val repository: ProfileRepository = mockk()
    private val useCase = GetProfileLikedPhotosUseCase(repository)
    private val username = "saiful"

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
        unmockkAll()
    }


    @Test
    fun `verify get profile liked photos use case`() {
        runTest {
            coEvery { repository.profileLikedPhotos(username) } returns response

            val result = useCase(username)

            assert(result.asSnapshot() == response.toPhotoItem().asSnapshot())
            coVerify(exactly = 1) { repository.profileLikedPhotos(username) }
        }
    }

    @Test
    fun `verify get profile liked photos use case returns empty paging data`() {
        runTest {
            coEvery {
                repository.profileLikedPhotos(username)
            } returns
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

            val result = useCase(username).asSnapshot(
                onError = { loadState ->
                    assert(loadState.refresh is LoadState.Error)
                    ErrorRecovery.RETURN_CURRENT_SNAPSHOT
                }
            )

            assert(result.isEmpty())
            coVerify(exactly = 1) { repository.profileLikedPhotos(username) }
        }
    }
}