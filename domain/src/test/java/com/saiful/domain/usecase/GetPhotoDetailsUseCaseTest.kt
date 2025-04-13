package com.saiful.domain.usecase

import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.whenever
import com.saiful.core.domain.Result
import com.saiful.data.model.*
import com.saiful.data.model.photo.Urls
import com.saiful.data.model.photo.details.*
import com.saiful.data.repository.photo.PhotoRepository
import com.saiful.domain.mapper.toPhotoDetailsItem
import com.saiful.test.unit.BaseUseCaseTest
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock

class GetPhotoDetailsUseCaseTest : BaseUseCaseTest() {

    private val photoRepository: PhotoRepository = mock()
    private val getPhotoDetailsUseCase = GetPhotoDetailsUseCase(photoRepository)
    private lateinit var response: PhotoDetails

    private lateinit var photoId: String
    override fun setup() {
        photoId = "1"
        response = PhotoDetails(
            id = "",
            altDescription = "",
            blurHash = "",
            color = "",
            height = 3,
            width = 4,
            createdAt = "",
            currentUserCollections = listOf(),
            description = "",
            likedByUser = false,
            likes = 33,
            links = null,
            promotedAt = "",
            slug = "",
            sponsorship = null,
            updatedAt = "",
            urls = Urls(
                full = "https://images.unsplash.com/photo-1506816694892-87a3e3c11a97?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=800&q=80",
                raw = "https://images.unsplash.com/photo-1506816694892-87a3e3c11a97?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=800&q=80",
                regular = "https://images.unsplash.com/photo-1506816694892-87a3e3c11a97?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=400&q=80",
                small = "https://images.unsplash.com/photo-1506816694892-87a3e3c11a97?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=200&q=80",
                thumb = "https://images.unsplash.com/photo-1506816694892-87a3e3c11a97?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=100&q=80"
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
            exif = Exif(
                camera = "Canon EOS 5D Mark IV",
                model = "Canon EOS 5D Mark IV",
                name = "Canon EOS 5D Mark IV",
                exposureTime = "1/100",
                aperture = 2.8,
                focalLength = 50.0,
                iso = 100
            ),
            location = null,
            tags = listOf(
                Tag(
                    type = "nature",
                    title = "Nature"
                ),
                Tag(
                    type = "landscape",
                    title = "Landscape"
                ),
                Tag(
                    type = "mountain",
                    title = "Mountain"
                )
            ),
            views = 33,
            downloads = 33,
        )
    }

    override fun tearDown() {
        reset(photoRepository)
    }

    @Test
    fun `verify get photo details use case returns photo details`() {
        runTest {
            whenever(photoRepository.photoDetails(photoId)).thenReturn(
                Result.Success(response)
            )

            val result = getPhotoDetailsUseCase(photoId)

            assert(result is Result.Success)
            assert((result as Result.Success).data == response.toPhotoDetailsItem())
        }
    }


    @Test
    fun `verify get photo details use case returns error`() {
        runTest {
            whenever(photoRepository.photoDetails(photoId)).thenReturn(
                Result.Error(domainException)
            )

            val result = getPhotoDetailsUseCase(photoId)

            assert(result is Result.Error)
            assert((result as Result.Error).error == domainException)
        }
    }

}