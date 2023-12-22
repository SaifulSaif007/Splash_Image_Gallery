package com.saiful.data.remote

import com.saiful.data.model.collection.Collection
import com.saiful.data.model.photo.Photo
import com.saiful.data.model.photo.details.PhotoDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("photos/{photoId}")
    suspend fun photoDetails(
        @Path("photoId") photoId: String
    ): PhotoDetails
}