package com.hivian.lydia_test.core.services.networking

import com.hivian.lydia_test.core.models.dto.RandomUserDTO

interface IHttpClient {

    var baseUrl: String

    suspend fun fetchRandomUsers(page: Int, results: Int): HttpResult<List<RandomUserDTO>>

}