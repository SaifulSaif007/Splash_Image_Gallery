package com.saiful.domain.usecase

import androidx.paging.*
import androidx.paging.testing.ErrorRecovery
import androidx.paging.testing.asSnapshot
import com.saiful.data.model.*
import com.saiful.data.model.collection.Collection
import com.saiful.data.model.collection.CollectionLinks
import com.saiful.data.model.photo.*
import com.saiful.data.repository.collection.CollectionRepository
import com.saiful.domain.mapper.toCollectionItem
import com.saiful.test.unit.BaseUseCaseTest
import io.mockk.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test


class GetCollectionUseCaseTest : BaseUseCaseTest() {

    private val collectionRepository: CollectionRepository = mockk()

    private val getCollectionUseCase = GetCollectionUseCase(collectionRepository)

    private lateinit var response: Flow<PagingData<Collection>>

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
        unmockkAll()
    }


    @Test
    fun `verify collection useCase returns collectionItem paging data`() {
        runTest {
            coEvery { collectionRepository.collectionList() } returns response

            val result = getCollectionUseCase(Unit)

            assert(result.asSnapshot() == response.toCollectionItem().asSnapshot())
            coVerify(exactly = 1) { collectionRepository.collectionList() }

        }
    }

    @Test
    fun `verify collection useCase return empty paging data when loadState error occurs`() {
        runTest {
            coEvery {
                collectionRepository.collectionList()
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


            val result = getCollectionUseCase(Unit).asSnapshot(
                onError = { loadState ->
                    assert(loadState.refresh is LoadState.Error)
                    ErrorRecovery.RETURN_CURRENT_SNAPSHOT
                }
            )

            assert(result.isEmpty())
            coVerify(exactly = 1) { collectionRepository.collectionList() }
        }
    }
}