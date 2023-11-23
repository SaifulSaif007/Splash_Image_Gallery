package com.saiful.domain.usecase

import androidx.paging.PagingData
import com.saiful.core.domain.UseCase
import com.saiful.data.repository.collection.CollectionRepository
import com.saiful.domain.mapper.toCollectionItem
import com.saiful.domain.model.CollectionItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollectionUseCase @Inject constructor(
    private val collectionRepo: CollectionRepository
) : UseCase<Unit, Flow<PagingData<CollectionItem>>>() {
    override suspend fun execute(params: Unit): Flow<PagingData<CollectionItem>> {
        return collectionRepo.collectionList().toCollectionItem()
    }

}