package com.saiful.domain.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.saiful.data.model.home.Photo
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
                mainImage = it.urls.small
            )
        }
    }