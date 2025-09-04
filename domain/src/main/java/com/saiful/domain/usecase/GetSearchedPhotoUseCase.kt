package com.saiful.domain.usecase

import androidx.paging.PagingData
import com.saiful.core.domain.UseCase
import com.saiful.data.repository.search.SearchRepository
import com.saiful.domain.mapper.toPhotoItem
import com.saiful.domain.mapper.toPhotoItems
import com.saiful.domain.model.PhotoItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchedPhotoUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) : UseCase<String, Flow<PagingData<PhotoItem>>>() {

    override suspend fun execute(params: String): Flow<PagingData<PhotoItem>> {
        return searchRepository.searchPhoto(params).toPhotoItems()
    }

}