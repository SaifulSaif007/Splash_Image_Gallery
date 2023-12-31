package com.saiful.data.repository.collection

import androidx.paging.LoadState
import androidx.paging.testing.ErrorRecovery
import androidx.paging.testing.asSnapshot
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.saiful.data.model.Links
import com.saiful.data.model.ProfileImage
import com.saiful.data.model.Social
import com.saiful.data.model.User
import com.saiful.data.model.collection.Collection
import com.saiful.data.model.collection.CollectionLinks
import com.saiful.data.model.photo.ContentLink
import com.saiful.data.model.photo.Photo
import com.saiful.data.model.photo.Urls
import com.saiful.data.remote.ApiService
import com.saiful.test.unit.BaseRepositoryTest
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CollectionRepositoryImplTest : BaseRepositoryTest() {

    private val apiService: ApiService = mock()

    private val page: Int = 1
    private val pageSize: Int = 10
    private lateinit var collectionRepository: CollectionRepository
    private lateinit var collectionResponse: List<Collection>
    private lateinit var collectionPhotoResponse: List<Photo>

    override fun setup() {
        collectionRepository = CollectionRepositoryImpl(
            apiService
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

        collectionResponse = listOf(
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
                tags = emptyList(),
                title = "title",
                totalPhotos = 124,
                updatedAt = "",
                user = user
            )
        )

        collectionPhotoResponse = listOf(
            photo
        )
    }

    override fun tearDown() {
        reset(apiService)
    }

    @Test
    fun `verify collection photos fetch is successful`() {
        runTest {
            whenever(
                apiService.collections(
                    page = page, pageSize = pageSize
                )
            ).thenReturn(collectionResponse)

            val res = collectionRepository.collectionList().asSnapshot()
            assert(res == collectionResponse)

            verify(
                apiService, times(1)
            ).collections(page = page, pageSize = pageSize)
        }
    }

    @Test
    fun `verify collection photos fetch is not successful`() {
        runTest {
            whenever(
                apiService.collections(
                    page = page, pageSize = pageSize
                )
            ).thenThrow(RuntimeException("Something went wrong"))

            val res = collectionRepository.collectionList().asSnapshot(
                onError = { loadState ->
                    assert(loadState.refresh is LoadState.Error)
                    ErrorRecovery.RETURN_CURRENT_SNAPSHOT
                }
            )

            assert(res.isEmpty())

            verify(
                apiService, times(1)
            ).collections(page = page, pageSize = pageSize)
        }
    }

    @Test
    fun `verify collection photo fetch is successful`() {
        runTest {
            whenever(
                apiService.collectionPhotos(
                    collectionId = "1",
                    page = page,
                    pageSize = pageSize
                )
            ).thenReturn(collectionPhotoResponse)

            val res = collectionRepository.collectionPhotos("1").asSnapshot()
            assert(res == collectionPhotoResponse)

            verify(
                apiService, times(1)
            ).collectionPhotos(collectionId = "1", page = page, pageSize = pageSize)
        }
    }

    @Test
    fun `verify collection photo fetch is not successful`() {
        runTest {
            whenever(
                apiService.collectionPhotos(
                    collectionId = "1",
                    page = page,
                    pageSize = pageSize
                )
            ).thenThrow(RuntimeException("Something went wrong"))

            val res = collectionRepository.collectionPhotos("1").asSnapshot(
                onError = { loadState ->
                    assert(loadState.refresh is LoadState.Error)
                    ErrorRecovery.RETURN_CURRENT_SNAPSHOT
                }
            )

            assert(res.isEmpty())

            verify(
                apiService, times(1)
            ).collectionPhotos(collectionId = "1", page = page, pageSize = pageSize)
        }
    }
}