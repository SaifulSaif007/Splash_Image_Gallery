package com.saiful.data.repository.collection

import androidx.paging.PagingData
import com.saiful.data.model.collection.Collection
import com.saiful.data.model.photo.Photo
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {
    suspend fun collectionList(): Flow<PagingData<Collection>>

    suspend fun collectionPhotos(collectionId: String): Flow<PagingData<Photo>>
}