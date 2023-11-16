package com.saiful.data.remote

import com.saiful.data.model.home.Photo
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ApiService {

    @GET("photos")
    suspend fun photos(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
        @Query("order_by") orderBy: String = "latest"
    ): List<Photo>

}