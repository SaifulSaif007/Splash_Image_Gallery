package com.saiful.data.repository

import com.saiful.core.components.logger.logError
import com.saiful.core.domain.Result
import com.saiful.core.utils.ErrorMapper
import com.saiful.data.mapper.toHomeItem
import com.saiful.data.remote.ApiService
import com.saiful.domain.model.HomeItem
import com.saiful.domain.repository.PhotoRepository
import javax.inject.Inject

internal class PhotoRepositoryImpl @Inject constructor(
    private val errorMapper: ErrorMapper,
    private val apiService: ApiService
) : PhotoRepository {
    override suspend fun photosList(): Result<List<HomeItem>> {
        return try {
            Result.Success(
                apiService.photos(
                    page = 1,
                    pageSize = 10
                ).toHomeItem()
            )
        } catch (ex: Exception) {
            logError(msg = ex.localizedMessage ?: toString())
            Result.Error(error = errorMapper.toDomainException(ex))
        }
    }
}