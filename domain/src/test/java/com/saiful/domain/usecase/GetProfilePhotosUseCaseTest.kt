package com.saiful.domain.usecase

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.testing.ErrorRecovery
import androidx.paging.testing.asSnapshot
import com.nhaarman.mockito_kotlin.only
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.saiful.data.model.Links
import com.saiful.data.model.ProfileImage
import com.saiful.data.model.Social
import com.saiful.data.model.User
import com.saiful.data.model.photo.ContentLink
import com.saiful.data.model.photo.Photo
import com.saiful.data.model.photo.Urls
import com.saiful.data.repository.profile.ProfileRepository
import com.saiful.domain.mapper.toPhotoItem
import com.saiful.test.unit.BaseUseCaseTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito

class GetProfilePhotosUseCaseTest : BaseUseCaseTest() {

    private val repository: ProfileRepository = Mockito.mock()
    private val useCase = GetProfilePhotosUseCase(repository)
    private val userName = "saiful"

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
        reset(repository)
    }

    @Test
    fun `verify get profile photos use case`() {
        runTest {
            whenever(repository.profilePhotos(username = userName)).thenReturn(response)

            val result = useCase.invoke(userName)

            assert(result.asSnapshot() == response.toPhotoItem().asSnapshot())
            verify(repository, only()).profilePhotos(username = userName)
        }
    }

    @Test
    fun `verify get profile photos use case return empty paging data`() {
        runTest {
            whenever(repository.profilePhotos(userName)).thenReturn(
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

            val result = useCase.invoke(userName).asSnapshot(
                onError = { loadState ->
                    assert(loadState.refresh is LoadState.Error)
                    ErrorRecovery.RETURN_CURRENT_SNAPSHOT
                }
            )

            assert(result.isEmpty())
            verify(repository, only()).profilePhotos(username = userName)
        }
    }

}