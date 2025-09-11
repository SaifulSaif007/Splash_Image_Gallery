package com.saiful.domain.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.saiful.data.model.search.SearchUser
import com.saiful.domain.model.SearchUserItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal fun Flow<PagingData<SearchUser.User>>.toSearchUserItem() =
    this.map { pagingData ->
        pagingData.map {
            SearchUserItem(
                userId = it.id,
                userName = it.username,
                name = it.name,
                profileImage = it.profileImage.small,
                photos = emptyList()
            )
        }
    }