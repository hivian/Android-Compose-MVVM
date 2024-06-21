package com.hivian_compose_mvvm.basic_feature.data.sources

import com.hivian.compose_mvvm.core.datasources.remote.HttpResult
import com.hivian.compose_mvvm.core.datasources.models.Results
import com.hivian.compose_mvvm.core.datasources.remote.IApiService
import com.hivian_compose_mvvm.basic_feature.domain.services.IRandomUsersHttpService

internal class RandomUsersHttpService(
    private val service: IApiService
): IRandomUsersHttpService {

    override suspend fun fetchRandomUsers(page: Int, results: Int): HttpResult<Results> {
        return safeApiCall {
            service.fetchRandomUsers(page = page, results = results)
        }
    }

}
