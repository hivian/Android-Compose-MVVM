package com.hivian.lydia_test.business.remote

import com.hivian.lydia_test.business.model.dto.Results
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/1.3")
    suspend fun fetchRandomUsers(
        @Query("seed") seed: String = "lydia",
        @Query("results") results: Int = 20,
        @Query("page") page: Int = 1): Results

}
