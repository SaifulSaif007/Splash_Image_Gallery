package com.saiful.data.remote

import com.saiful.data.model.home.PhotosResponse
import retrofit2.http.POST
import retrofit2.http.Query

internal interface ApiService {
    @POST("photos")
    suspend fun Photos(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
        @Query("order_by") orderBy: String = "latest"
    ): PhotosResponse

}