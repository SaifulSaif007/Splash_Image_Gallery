package com.saiful.domain.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.saiful.data.model.collection.Collection
import com.saiful.domain.model.CollectionItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal fun Flow<PagingData<Collection>>.toCollectionItem() =
    this.map { pagingData ->
        pagingData.map {
            CollectionItem(
                profileImage = it.user.profileImage.small,
                profileName = it.user.name,
                mainImage = it.coverPhoto.urls.small,
                title = it.title,
                totalPhoto = it.totalPhotos
            )
        }

    }