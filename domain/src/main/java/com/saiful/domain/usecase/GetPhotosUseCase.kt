package com.saiful.domain.usecase

import com.saiful.core.domain.Result
import com.saiful.core.domain.UseCase
import com.saiful.data.model.param.HomeParams
import com.saiful.data.repository.PhotoRepository
import com.saiful.domain.mapper.toHomeItem
import com.saiful.domain.model.HomeItem
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) : UseCase<Pair<Int, Int>, Result<List<HomeItem>>>() {
    override suspend fun execute(params: Pair<Int, Int>): Result<List<HomeItem>> {
        return when (val response = photoRepository.photosList(
            HomeParams(
                page = params.first, pageSize = params.second
            )
        )) {
            is Result.Success -> Result.Success(response.data.toHomeItem())

            is Result.Error -> Result.Error(response.error)
        }
    }

}