package com.saiful.domain.usecase

import com.saiful.core.domain.Result
import com.saiful.core.domain.UseCase
import com.saiful.data.repository.profile.ProfileRepository
import com.saiful.domain.mapper.toProfileInfo
import com.saiful.domain.model.ProfileInfo
import javax.inject.Inject

class GetProfileInfoUseCase @Inject constructor(
    private val repository: ProfileRepository
) : UseCase<String, Result<ProfileInfo>>() {
    override suspend fun execute(params: String): Result<ProfileInfo> {
        return when (val result = repository.profile(params)) {
            is Result.Error -> {
                Result.Error(result.error)
            }

            is Result.Success -> {
                Result.Success(result.data.toProfileInfo())
            }
        }
    }

}