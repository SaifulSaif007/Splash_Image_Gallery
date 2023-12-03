package com.saiful.domain.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.saiful.data.model.photo.Photo
import com.saiful.domain.model.HomeItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal fun Flow<PagingData<Photo>>.toPhotoItem() =
    this.map { pagingData ->
        pagingData.map {
            HomeItem(
                profileImage = it.user.profileImage.small,
                profileName = it.user.name,
                sponsored = it.sponsorship != null,
                mainImage = it.urls.regular,
                mainImageBlurHash = it.blurHash ?: "",
                mainImageHeight = ((it.height.toDouble() / it.width.toDouble()) * 10).toInt(),
                mainImageWidth = (it.width / it.width) * 10,
            )
        }
    }