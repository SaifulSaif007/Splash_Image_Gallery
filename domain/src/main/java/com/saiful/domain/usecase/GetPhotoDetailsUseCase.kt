package com.saiful.domain.usecase

import com.saiful.core.domain.Result
import com.saiful.core.domain.UseCase
import com.saiful.data.repository.photo.PhotoRepository
import com.saiful.domain.mapper.toPhotoDetailsItem
import com.saiful.domain.model.PhotoDetailsItem
import javax.inject.Inject

typealias photoId = String

class GetPhotoDetailsUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) : UseCase<photoId, Result<PhotoDetailsItem>>() {
    override suspend fun execute(params: String): Result<PhotoDetailsItem> {
        return when (val result = photoRepository.photoDetails(params)) {
            is Result.Success -> {
                Result.Success(result.data.toPhotoDetailsItem())
            }

            is Result.Error -> {
                Result.Error(result.error)
            }
        }
    }
}

