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
                collectionId = it.id,
                profileImage = it.user.profileImage.small,
                profileName = it.user.name,
                mainImage = it.coverPhoto.urls.regular,
                mainImageBlurHash = it.coverPhoto.blurHash ?: "",
                mainImageHeight =((it.coverPhoto.height.toDouble() / it.coverPhoto.width.toDouble()) * 10).toInt(),
                mainImageWidth = (it.coverPhoto.width / it.coverPhoto.width) * 10,
                title = it.title,
                totalPhoto = it.totalPhotos
            )
        }

    }