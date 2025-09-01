package com.saiful.data.repository.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.saiful.data.model.User
import com.saiful.data.model.collection.Collection
import com.saiful.data.model.search.SearchedPhoto
import com.saiful.data.remote.ApiService
import com.saiful.data.repository.pager.GenericPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SearchRepository {
    override suspend fun searchPhoto(query: String): Flow<PagingData<SearchedPhoto.Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100
            ),
            pagingSourceFactory = {
                GenericPagingSource { page, pageSize ->
                    apiService.searchPhoto(query, page, pageSize).result
                }
            }
        ).flow
    }

    override suspend fun searchCollection(query: String): Flow<PagingData<Collection>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchUser(query: String): Flow<PagingData<User>> {
        TODO("Not yet implemented")
    }
}