package com.saiful.domain.mapper

import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.testing.asSnapshot
import com.saiful.data.model.Links
import com.saiful.data.model.ProfileImage
import com.saiful.data.model.Social
import com.saiful.data.model.User
import com.saiful.data.model.photo.ContentLink
import com.saiful.data.model.photo.Photo
import com.saiful.data.model.photo.Urls
import com.saiful.domain.model.PhotoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PhotoMapperKtTest {

    private val photos: Flow<PagingData<Photo>> = flowOf(
        PagingData.from(
            listOf(
                Photo(
                    id = "1",
                    altDescription = "",
                    blurHash = "",
                    color = "",
                    height = 11316,
                    width = 7125,
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


    private val expectedList = photos.map { pagingData ->
        pagingData.map {
            PhotoItem(
                photoId = it.id,
                profileImage = it.user.profileImage.small,
                profileName = it.user.name,
                sponsored = it.sponsorship != null,
                mainImage = it.urls.regular,
                mainImageBlurHash = "",
                mainImageHeight = ((it.height.toDouble() / it.width.toDouble()) * 10).toInt(),
                mainImageWidth = (it.width / it.width) * 10,
                profileUserName = it.user.username,
            )
        }
    }

    @Test
    fun `verify toPhotoItem() maps properly`() {
        runTest {
            assert(photos.toPhotoItem().asSnapshot() == expectedList.asSnapshot())
        }

    }
}