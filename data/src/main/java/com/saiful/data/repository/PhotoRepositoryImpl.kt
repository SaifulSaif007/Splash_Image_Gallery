package com.saiful.data.repository

import com.saiful.core.domain.Result
import com.saiful.core.utils.ErrorMapper
import com.saiful.data.model.home.Photo
import com.saiful.data.model.param.HomeParams
import com.saiful.data.remote.ApiService
import javax.inject.Inject

internal class PhotoRepositoryImpl @Inject constructor(
    private val errorMapper: ErrorMapper,
    private val apiService: ApiService
) : PhotoRepository {
    override suspend fun photosList(homeParams: HomeParams): Result<List<Photo>> {
        return try {
            Result.Success(
                apiService.photos(
                    page = homeParams.page,
                    pageSize = homeParams.pageSize
                )
            )
        } catch (ex: Exception) {
            Result.Error(error = errorMapper.toDomainException(ex))
        }
    }
}
