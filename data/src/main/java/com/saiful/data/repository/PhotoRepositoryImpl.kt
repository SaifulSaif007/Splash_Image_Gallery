package com.saiful.data.repository

import androidx.paging.*
import com.saiful.data.model.home.Photo
import com.saiful.data.remote.ApiService
import com.saiful.data.repository.pager.PhotoPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class PhotoRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PhotoRepository {
    override suspend fun photosList(): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100
            ),
            pagingSourceFactory = {
                PhotoPagingSource(
                    apiService = apiService
                )
            }
        ).flow
    }
}
