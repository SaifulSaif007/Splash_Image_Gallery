package com.saiful.data.repository.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.saiful.data.model.search.SearchCollection
import com.saiful.data.model.search.SearchPhoto
import com.saiful.data.model.search.SearchUser
import com.saiful.data.remote.ApiService
import com.saiful.data.repository.pager.GenericPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SearchRepository {
    override suspend fun searchPhoto(query: String): Flow<PagingData<SearchPhoto.Photo>> {
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

    override suspend fun searchCollection(query: String): Flow<PagingData<SearchCollection.Collection>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100
            ),
            pagingSourceFactory = {
                GenericPagingSource { page, pageSize ->
                    apiService.searchCollection(query, page, pageSize).result
                }
            }
        ).flow
    }

    override suspend fun searchUser(query: String): Flow<PagingData<SearchUser.User>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100
            ),
            pagingSourceFactory = {
                GenericPagingSource { page, pageSize ->
                    apiService.searchUser(query, page, pageSize).result.map {
                        it.apply {
                            if (page == 1) it.photo = apiService.profilePhotos(it.username, 1, 3)
                            else it.photo = emptyList()
                        }
                    }
                }
            }
        ).flow
    }
}