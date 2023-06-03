package com.hivian_compose_mvvm.basic_feature.data.sources.remote

import com.hivian.compose_mvvm.core.datasource.HttpResult
import com.hivian.compose_mvvm.core.datasource.models.Results
import com.hivian.compose_mvvm.core.datasource.remote.IApiService
import com.hivian_compose_mvvm.basic_feature.domain.services.IRandomUsersHttpService
import javax.inject.Inject

internal class RandomUsersHttpService @Inject constructor(
    private val service: IApiService
): IRandomUsersHttpService {

    override suspend fun fetchRandomUsers(page: Int, results: Int): HttpResult<Results> {
        return safeApiCall {
            service.fetchRandomUsers(page = page, results = results)
        }
    }

}
