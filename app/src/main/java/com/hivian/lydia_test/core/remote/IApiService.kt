package com.hivian.lydia_test.core.remote

import com.hivian.lydia_test.core.models.dto.Results
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiService {

    @GET("api/1.3")
    suspend fun fetchRandomUsers(
        @Query("seed") seed: String = "lydia",
        @Query("results") results: Int = 20,
        @Query("page") page: Int = 1): Response<Results>

}
