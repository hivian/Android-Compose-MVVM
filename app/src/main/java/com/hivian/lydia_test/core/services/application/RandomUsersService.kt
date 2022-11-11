package com.hivian.lydia_test.core.services.application

import com.hivian.lydia_test.core.models.dto.RandomUserDTO
import com.hivian.lydia_test.core.models.dto.Results
import com.hivian.lydia_test.core.servicelocator.IoC
import com.hivian.lydia_test.core.services.database.IDatabaseService
import com.hivian.lydia_test.core.services.networking.IHttpClient
import com.hivian.lydia_test.core.services.networking.ResourceErrorType
import com.hivian.lydia_test.core.services.networking.ServiceResult

internal class RandomUsersService: IRandomUsersService {

    private val database: IDatabaseService
        get() = IoC.resolve()

    private val httpClient: IHttpClient
        get() = IoC.resolve()

    override suspend fun fetchRandomUsers(page: Int, results: Int): ServiceResult<List<RandomUserDTO>> {
        val httpResult = httpClient.fetchRandomUsers(page, results)

        if (httpResult is ServiceResult.Success) {
            database.upsertUsers(httpResult.data.results)
        }

        val databaseUsers = database.fetchUsers().ifEmpty {
            return ServiceResult.Error(ResourceErrorType.NO_RESULT)
        }

        return ServiceResult.Success(databaseUsers)
    }

}
