package com.saiful.data.repository.photo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.saiful.core.components.logger.logError
import com.saiful.core.domain.Result
import com.saiful.core.utils.ErrorMapper
import com.saiful.data.model.photo.Photo
import com.saiful.data.model.photo.details.PhotoDetails
import com.saiful.data.remote.ApiService
import com.saiful.data.repository.pager.GenericPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class PhotoRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val errorMapper: ErrorMapper
) : PhotoRepository {

    override suspend fun photosList(): Flow<PagingData<Photo>> {
        val service = apiService::photos

        return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100
            ),
            pagingSourceFactory = {
                GenericPagingSource(service)
            }
        ).flow
    }

    override suspend fun photoDetails(photoId: String): Result<PhotoDetails> {
        return try {
            Result.Success(apiService.photoDetails(photoId))
        } catch (ex: Exception) {
            logError(msg = ex.toString())
            Result.Error(errorMapper.toDomainException(ex))
        }
    }
}
