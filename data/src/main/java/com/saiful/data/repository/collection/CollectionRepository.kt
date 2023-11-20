package com.saiful.data.repository.collection

import androidx.paging.PagingData
import com.saiful.data.model.collection.Collection
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {
    suspend fun collectionList(): Flow<PagingData<Collection>>
}