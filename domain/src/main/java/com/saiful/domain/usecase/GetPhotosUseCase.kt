package com.saiful.domain.usecase

import com.saiful.core.domain.Result
import com.saiful.core.domain.UseCase
import com.saiful.data.repository.PhotoRepository
import com.saiful.domain.mapper.toHomeItem
import com.saiful.domain.model.HomeItem
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) : UseCase<Unit, Result<List<HomeItem>>>() {
    override suspend fun execute(params: Unit): Result<List<HomeItem>> {
        return when (val response = photoRepository.photosList()) {
            is Result.Success -> Result.Success(response.data.toHomeItem())

            is Result.Error -> Result.Error(response.error)
        }
    }

}