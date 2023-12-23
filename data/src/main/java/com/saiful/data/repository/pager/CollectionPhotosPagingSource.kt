package com.saiful.data.repository.pager

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.saiful.core.components.logger.logError
import com.saiful.data.model.photo.Photo
import com.saiful.data.remote.ApiService

internal class CollectionPhotosPagingSource(
    private val apiService: ApiService,
    private val collectionId: String
) : PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val pageCount = params.key ?: START_PAGE_INDEX

        return try {
            val response = apiService.collectionPhotos(
                collectionId = collectionId,
                page = pageCount,
                pageSize = PAGE_SIZE
            )

            LoadResult.Page(
                data = response,
                prevKey = if (pageCount == START_PAGE_INDEX) null else pageCount - 1,
                nextKey = if (response.size < PAGE_SIZE) null else pageCount + 1
            )

        } catch (ex: Exception) {
            logError(msg = ex.message.toString())
            LoadResult.Error(ex)
        }
    }

    private companion object {
        const val START_PAGE_INDEX = 1
        const val PAGE_SIZE = 10
    }
}