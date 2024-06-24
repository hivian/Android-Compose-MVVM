package com.hivian.randomusers.core.data.remote

import com.hivian.randomusers.core.data.models.Results
import retrofit2.http.GET
import retrofit2.http.Query

interface IHttpClient {

    @Throws(Exception::class)
    @GET("api/1.3")
    suspend fun fetchRandomUsers(
        @Query("seed") seed: String = "easypeasy",
        @Query("results") results: Int = 20,
        @Query("page") page: Int = 1): Results

}
