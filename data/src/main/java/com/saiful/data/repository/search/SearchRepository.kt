package com.saiful.data.repository.search

import androidx.paging.PagingData
import com.saiful.data.model.User
import com.saiful.data.model.collection.Collection
import com.saiful.data.model.search.SearchCollection
import com.saiful.data.model.search.SearchPhoto
import com.saiful.data.model.search.SearchUser
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun searchPhoto(query: String): Flow<PagingData<SearchPhoto.Photo>>

    suspend fun searchCollection(query: String): Flow<PagingData<SearchCollection.Collection>>

    suspend fun searchUser(query: String): Flow<PagingData<SearchUser.User>>
}