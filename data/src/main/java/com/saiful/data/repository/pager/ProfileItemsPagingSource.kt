package com.saiful.data.repository.pager

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.saiful.core.components.logger.logError

internal class ProfileItemsPagingSource<T : Any>(
    private val apiCall: suspend (userName: String, page: Int, pageSize: Int) -> List<T>,
    private val userName: String
) : PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val pageCount = params.key ?: START_PAGE_INDEX

        return try {
            val response = apiCall.invoke(userName, pageCount, PAGE_SIZE)

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