package com.saiful.data.repository.profile

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.saiful.core.domain.Result
import com.saiful.core.utils.ErrorMapper
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
}