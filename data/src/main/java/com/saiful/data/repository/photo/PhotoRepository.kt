package com.saiful.data.repository.photo

import androidx.paging.PagingData
import com.saiful.core.domain.Result
import com.saiful.data.model.photo.Photo
import com.saiful.data.model.photo.details.PhotoDetails
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    suspend fun photosList(): Flow<PagingData<Photo>>

    suspend fun photoDetails(photoId: String): Result<PhotoDetails>
}