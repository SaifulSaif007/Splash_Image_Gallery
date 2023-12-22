package com.saiful.domain.mapper

import com.saiful.data.model.Links
import com.saiful.data.model.ProfileImage
import com.saiful.data.model.Social
import com.saiful.data.model.User
import com.saiful.data.model.photo.Urls
import com.saiful.data.model.photo.details.Exif
import com.saiful.data.model.photo.details.PhotoDetails
import com.saiful.data.model.photo.details.Tag
import com.saiful.data.utils.UNKNOWN
import com.saiful.domain.model.PhotoDetailsItem
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PhotoDetailsMapperKtTest {
    private val photoDetails = PhotoDetails(
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

    private val expectedPhotoDetails =
        with(photoDetails) {
            PhotoDetailsItem(
                profileName = this.user.name,
                profileImage = this.user.profileImage.small,
                mainImage = this.urls.regular,
                thumbnailImage = this.urls.thumb,
                camera = (exif.model ?: UNKNOWN).trim(),
                aperture = if (exif.aperture != null) "f/${exif.aperture}" else UNKNOWN,
                focalLength = if (exif.focalLength != null) "${exif.focalLength}mm" else UNKNOWN,
                shutterSpeed = if (exif.exposureTime != null) "${exif.exposureTime}s" else UNKNOWN,
                iso = if (exif.iso != null) "${exif.iso}" else UNKNOWN,
                views = "$views",
                dimensions = this.width.toString() + " x " + this.height.toString(),
                downloads = this.downloads.toString(),
                likes = this.likes.toString(),
                tags = this.tags.map { tag ->
                    tag.title
                }
            )
        }


    @Test
    fun `verify toPhotoDetailsItem() maps properly`() {
        runTest {
            assert(photoDetails.toPhotoDetailsItem() == expectedPhotoDetails)
        }

    }
}

