package com.saiful.data.repository.profile

import androidx.paging.*
import com.saiful.core.components.logger.logError
import com.saiful.core.domain.Result
import com.saiful.core.utils.ErrorMapper
import com.saiful.data.model.collection.Collection
import com.saiful.data.model.photo.Photo
import com.saiful.data.model.profile.Profile
import com.saiful.data.remote.ApiService
import com.saiful.data.repository.pager.ProfileItemsPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class ProfileRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val errorMapper: ErrorMapper
) : ProfileRepository {
    override suspend fun profile(username: String): Result<Profile> {
        return try {
            Result.Success(apiService.profile(username))

        } catch (ex: Exception) {
            logError(msg = ex.message.toString())
            Result.Error(errorMapper.toDomainException(ex))
        }
    }

    override suspend fun profilePhotos(username: String): Flow<PagingData<Photo>> {
        val service = apiService::profilePhotos
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
            ),
            pagingSourceFactory = {
                ProfileItemsPagingSource(apiCall = service, userName = username)
            }
        ).flow
    }

    override suspend fun profileLikedPhotos(username: String): Flow<PagingData<Photo>> {
        val apiService = apiService::profileLikedPhotos

        return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
            ),
            pagingSourceFactory = {
                ProfileItemsPagingSource(apiCall = apiService, userName = username)
            }
        ).flow
    }

    override suspend fun profilePhotoCollections(username: String): Flow<PagingData<Collection>> {
        val apiService = apiService::profileCollections

        return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
            ),
            pagingSourceFactory = {
                ProfileItemsPagingSource(apiCall = apiService, userName = username)
            }
        ).flow
    }
}