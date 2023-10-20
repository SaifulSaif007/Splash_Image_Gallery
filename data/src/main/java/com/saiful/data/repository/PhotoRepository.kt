package com.saiful.data.repository

import com.saiful.core.domain.Result
import com.saiful.data.model.home.Photo
import com.saiful.data.model.param.HomeParams

interface PhotoRepository {
    suspend fun photosList(homeParams: HomeParams): Result<List<Photo>>
}