package com.saiful.domain.usecase

import androidx.paging.PagingData
import com.saiful.core.domain.UseCase
import com.saiful.data.repository.photo.PhotoRepository
import com.saiful.domain.mapper.toPhotoItem
import com.saiful.domain.model.PhotoItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) : UseCase<Unit, Flow<PagingData<PhotoItem>>>() {
    override suspend fun execute(params: Unit): Flow<PagingData<PhotoItem>> {
        return photoRepository.photosList().toPhotoItem()
    }
}