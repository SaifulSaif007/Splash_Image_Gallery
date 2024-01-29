package com.saiful.data.repository.profile

import com.saiful.core.domain.Result
import com.saiful.data.model.profile.Profile

interface ProfileRepository {
    suspend fun profile(username: String): Result<Profile>

}