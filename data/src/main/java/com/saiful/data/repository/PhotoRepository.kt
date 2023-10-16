package com.saiful.data.repository

import com.saiful.core.domain.Result
import com.saiful.data.model.home.Photo

interface PhotoRepository {
    suspend fun photosList(): Result<List<Photo>>
}