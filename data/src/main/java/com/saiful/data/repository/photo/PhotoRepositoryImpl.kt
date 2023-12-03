package com.saiful.data.repository.photo

import androidx.paging.*
import com.saiful.data.model.photo.Photo
import com.saiful.data.remote.ApiService
import com.saiful.data.repository.pager.GenericPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class PhotoRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PhotoRepository {
    override suspend fun photosList(): Flow<PagingData<Photo>> {
        val service = apiService::photos

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
