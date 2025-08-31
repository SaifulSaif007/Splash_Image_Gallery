package com.saiful.data.repository.search

import androidx.paging.PagingData
import com.saiful.data.model.User
import com.saiful.data.model.photo.Photo
import com.saiful.data.model.collection.Collection
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun searchPhoto(query: String): Flow<PagingData<Photo>>

    suspend fun searchCollection(query: String): Flow<PagingData<Collection>>

    suspend fun searchUser(query: String): Flow<PagingData<User>>
}