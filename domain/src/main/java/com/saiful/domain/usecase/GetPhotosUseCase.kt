package com.saiful.domain.usecase

import androidx.paging.PagingData
import com.saiful.core.domain.UseCase
import com.saiful.data.repository.PhotoRepository
import com.saiful.domain.mapper.toPhotoItem
import com.saiful.domain.model.HomeItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) : UseCase<Unit, Flow<PagingData<HomeItem>>>() {
    override suspend fun execute(params: Unit): Flow<PagingData<HomeItem>> {
        return photoRepository.photosList().toPhotoItem()
    }
}