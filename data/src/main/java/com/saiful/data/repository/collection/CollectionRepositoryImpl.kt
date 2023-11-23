package com.saiful.data.repository.collection

import androidx.paging.*
import com.saiful.data.model.collection.Collection
import com.saiful.data.remote.ApiService
import com.saiful.data.repository.pager.GenericPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class CollectionRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CollectionRepository {
    override suspend fun collectionList(): Flow<PagingData<Collection>> {
        val service = apiService::collections
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100
            ),
            pagingSourceFactory = {
                GenericPagingSource(service)
            }
        ).flow
    }
}