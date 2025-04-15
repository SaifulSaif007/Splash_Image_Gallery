package com.saiful.data.repository.collection

import androidx.paging.LoadState
import androidx.paging.testing.ErrorRecovery
import androidx.paging.testing.asSnapshot
import com.saiful.data.model.*
import com.saiful.data.model.collection.Collection
import com.saiful.data.model.collection.CollectionLinks
import com.saiful.data.model.photo.*
import com.saiful.data.remote.ApiService
import com.saiful.test.unit.BaseRepositoryTest
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CollectionRepositoryImplTest : BaseRepositoryTest() {

    private val apiService: ApiService = mockk()

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
        clearAllMocks()
    }

    @Test
    fun `verify collection photos fetch is successful`() {
        runTest {
            coEvery {
                apiService.collections(
                    page = page, pageSize = pageSize
                )
            } returns collectionResponse

            val res = collectionRepository.collectionList().asSnapshot()
            assert(res == collectionResponse)

            coVerify(exactly = 1) {
                apiService.collections(page = page, pageSize = pageSize)
            }
        }
    }

    @Test
    fun `verify collection photos fetch is not successful`() {
        runTest {
            coEvery {
                apiService.collections(
                    page = page, pageSize = pageSize
                )
            } throws RuntimeException("Something went wrong")

            val res = collectionRepository.collectionList().asSnapshot(
                onError = { loadState ->
                    assert(loadState.refresh is LoadState.Error)
                    ErrorRecovery.RETURN_CURRENT_SNAPSHOT
                }
            )

            assert(res.isEmpty())

            coVerify(exactly = 1) {
                apiService.collections(page = page, pageSize = pageSize)
            }
        }
    }

    @Test
    fun `verify collection photo fetch is successful`() {
        runTest {
            coEvery {
                apiService.collectionPhotos(
                    collectionId = "1",
                    page = page,
                    pageSize = pageSize
                )
            } returns collectionPhotoResponse

            val res = collectionRepository.collectionPhotos("1").asSnapshot()
            assert(res == collectionPhotoResponse)

            coVerify(exactly = 1) {
                apiService.collectionPhotos(collectionId = "1", page = page, pageSize = pageSize)
            }
        }
    }

    @Test
    fun `verify collection photo fetch is not successful`() {
        runTest {
            coEvery {
                apiService.collectionPhotos(
                    collectionId = "1",
                    page = page,
                    pageSize = pageSize
                )
            } throws RuntimeException("Something went wrong")

            val res = collectionRepository.collectionPhotos("1").asSnapshot(
                onError = { loadState ->
                    assert(loadState.refresh is LoadState.Error)
                    ErrorRecovery.RETURN_CURRENT_SNAPSHOT
                }
            )

            assert(res.isEmpty())

            coVerify(exactly = 1){
                apiService.collectionPhotos(collectionId = "1", page = page, pageSize = pageSize)
            }
        }
    }
}