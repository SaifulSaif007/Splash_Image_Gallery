package com.saiful.domain.usecase

import com.saiful.core.domain.Result
import com.saiful.core.domain.UseCase
import com.saiful.domain.model.HomeItem
import com.saiful.domain.repository.PhotoRepository
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) : UseCase<Unit, Result<List<HomeItem>>>() {
    override suspend fun execute(params: Unit): Result<List<HomeItem>> {
        return photoRepository.photosList()
    }

}