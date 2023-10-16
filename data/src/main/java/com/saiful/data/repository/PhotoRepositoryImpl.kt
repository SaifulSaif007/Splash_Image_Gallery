package com.saiful.data.repository

import com.saiful.core.components.logger.logError
import com.saiful.core.domain.Result
import com.saiful.core.utils.ErrorMapper
import com.saiful.data.model.home.Photo
import com.saiful.data.remote.ApiService
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val errorMapper: ErrorMapper,
    private val apiService: ApiService
) : PhotoRepository {
    override suspend fun photosList(): Result<List<Photo>> {
        return try {
            Result.Success(
                apiService.photos(
                    page = 1,
                    pageSize = 10
                )
            )
        } catch (ex: Exception) {
            logError(msg = ex.localizedMessage ?: toString())
            Result.Error(error = errorMapper.toDomainException(ex))
        }
    }
}
