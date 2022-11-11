package com.hivian.lydia_test.core.services.networking

import com.hivian.lydia_test.core.models.dto.Results

interface IHttpClient {

    var baseUrl: String

    suspend fun fetchRandomUsers(page: Int, results: Int): ServiceResult<Results>

}
