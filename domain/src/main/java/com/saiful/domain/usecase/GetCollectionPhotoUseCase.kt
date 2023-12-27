package com.saiful.domain.usecase

import androidx.paging.PagingData
import com.saiful.core.domain.UseCase
import com.saiful.data.repository.collection.CollectionRepository
import com.saiful.domain.mapper.toPhotoItem
import com.saiful.domain.model.PhotoItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias collectionId = String

class GetCollectionPhotoUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
) : UseCase<collectionId, Flow<PagingData<PhotoItem>>>() {

    override suspend fun execute(params: collectionId): Flow<PagingData<PhotoItem>> {
        return collectionRepository.collectionPhotos(params).toPhotoItem()
    }

}