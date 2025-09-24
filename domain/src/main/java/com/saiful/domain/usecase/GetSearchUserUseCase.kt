package com.saiful.domain.usecase

import androidx.paging.PagingData
import com.saiful.core.domain.UseCase
import com.saiful.data.repository.search.SearchRepository
import com.saiful.domain.mapper.toSearchUserItem
import com.saiful.domain.model.SearchUserItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchUserUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) : UseCase<String, Flow<PagingData<SearchUserItem>>>() {

    override suspend fun execute(params: String): Flow<PagingData<SearchUserItem>> {
        return searchRepository.searchUser(params).toSearchUserItem()
    }

}