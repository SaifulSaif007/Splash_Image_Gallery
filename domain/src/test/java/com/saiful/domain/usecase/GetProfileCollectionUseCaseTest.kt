package com.saiful.domain.usecase

import androidx.paging.*
import androidx.paging.testing.ErrorRecovery
import androidx.paging.testing.asSnapshot
import com.nhaarman.mockito_kotlin.only
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.saiful.data.model.*
import com.saiful.data.model.collection.Collection
import com.saiful.data.model.collection.CollectionLinks
import com.saiful.data.model.photo.*
import com.saiful.data.repository.profile.ProfileRepository
import com.saiful.domain.mapper.toCollectionItem
import com.saiful.test.unit.BaseUseCaseTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock

class GetProfileCollectionUseCaseTest : BaseUseCaseTest() {

    private val repository: ProfileRepository = mock()
    private val useCase = GetProfileCollectionUseCase(repository)

    private lateinit var response: Flow<PagingData<Collection>>
    private val userName = "saiful"

    override fun setup() {
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

        val photo = Photo(
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
        )

        response = flowOf(
            PagingData.from(
                listOf(
                    Collection(
                        id = "1",
                        coverPhoto = photo,
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
            )
        )
    }

    override fun tearDown() {
        reset(repository)
    }

    @Test
    fun `verify get profile collection use case returns paging data`() {
        runTest {
            whenever(repository.profilePhotoCollections(userName)).thenReturn(response)

            val result = useCase.invoke(userName)

            assert(result.asSnapshot() == response.toCollectionItem().asSnapshot())
            verify(repository, only()).profilePhotoCollections(userName)
        }
    }

    @Test
    fun `verify get profile collection use case returns empty paging data`() {
        runTest {
            whenever(repository.profilePhotoCollections(userName)).thenReturn(
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
            verify(repository, only()).profilePhotoCollections(userName)
        }
    }
}