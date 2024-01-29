package com.saiful.data.remote

import com.saiful.data.model.collection.Collection
import com.saiful.data.model.photo.Photo
import com.saiful.data.model.photo.details.PhotoDetails
import com.saiful.data.model.profile.Profile
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ApiService {

    @GET("photos")
    suspend fun photos(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
    ): List<Photo>

    @GET("photos/{photoId}")
    suspend fun photoDetails(
        @Path("photoId") photoId: String
    ): PhotoDetails

    @GET("collections")
    suspend fun collections(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
    ): List<Collection>

    @GET("collections/{collectionId}/photos")
    suspend fun collectionPhotos(
        @Path("collectionId") collectionId: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): List<Photo>

    @GET("users/{user_name}")
    suspend fun profile(
        @Path("user_name") userName: String
    ): Profile

}