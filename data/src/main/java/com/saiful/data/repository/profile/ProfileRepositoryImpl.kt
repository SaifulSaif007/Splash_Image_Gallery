package com.saiful.data.repository.profile

import com.saiful.core.domain.Result
import com.saiful.core.utils.ErrorMapper
import com.saiful.data.model.profile.Profile
import com.saiful.data.remote.ApiService
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
}