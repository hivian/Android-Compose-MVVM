package com.hivian.lydia_test.business.remote

import com.hivian.lydia_test.business.model.dto.RandomUserDTO
import com.hivian.common.IRestClient


class HttpRetrofit(private val service: ApiService): IRestClient<RandomUserDTO> {

    override suspend fun fetchRandomUsers(page: Int, results: Int): List<RandomUserDTO> =
        service.fetchRandomUsers(page = page, results = results).results

}
