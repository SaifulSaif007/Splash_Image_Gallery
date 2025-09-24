package com.saiful.domain.usecase

import androidx.paging.PagingData
import com.saiful.core.domain.UseCase
import com.saiful.data.repository.search.SearchRepository
import com.saiful.domain.mapper.toSearchCollectionItem
import com.saiful.domain.model.CollectionItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchCollectionUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) : UseCase<String, Flow<PagingData<CollectionItem>>>() {

    override suspend fun execute(params: String): Flow<PagingData<CollectionItem>> {
        return searchRepository.searchCollection(params).toSearchCollectionItem()
    }

}