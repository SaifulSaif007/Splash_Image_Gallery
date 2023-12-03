package com.saiful.data.remote

import com.saiful.data.model.collection.Collection
import com.saiful.data.model.photo.Photo
import com.saiful.data.model.photo.details.PhotoDetails
import retrofit2.http.*

internal interface ApiService {

    @GET("photos")
    suspend fun photos(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
    ): List<Photo>

    @GET("collections")
    suspend fun collections(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
    ): List<Collection>

    @GET("photos")
    suspend fun photoDetails(
        @Path("photoId") photoId: String
    ): PhotoDetails
}