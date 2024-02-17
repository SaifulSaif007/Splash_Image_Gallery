package com.saiful.data.repository.profile

import androidx.paging.PagingData
import com.saiful.core.domain.Result
import com.saiful.data.model.photo.Photo
import com.saiful.data.model.profile.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun profile(username: String): Result<Profile>

    suspend fun profilePhotos(username: String): Flow<PagingData<Photo>>
    suspend fun profileLikedPhotos(username: String): Flow<PagingData<Photo>>
}