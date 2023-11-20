package com.saiful.data.repository.photo

import androidx.paging.PagingData
import com.saiful.data.model.home.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    suspend fun photosList(): Flow<PagingData<Photo>>
}