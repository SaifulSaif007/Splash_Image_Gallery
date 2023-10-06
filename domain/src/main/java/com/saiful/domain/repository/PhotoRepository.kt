package com.saiful.domain.repository

import com.saiful.core.domain.Result
import com.saiful.domain.model.HomeItem

interface PhotoRepository {
    suspend fun photosList(): Result<HomeItem>
}