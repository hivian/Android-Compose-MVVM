package com.hivian.randomusers.homefeature.data.services

import com.hivian.randomusers.core.data.ServiceResult
import com.hivian.randomusers.core.data.remote.ErrorType
import com.hivian.randomusers.core.data.remote.HttpResult
import com.hivian.randomusers.core.domain.services.IApiService
import com.hivian.randomusers.core.domain.services.IDatabaseService
import com.hivian.randomusers.homefeature.data.mappers.ImageSize
import com.hivian.randomusers.homefeature.data.mappers.mapToRandomUser
import com.hivian.randomusers.homefeature.data.mappers.mapToRandomUsers
import com.hivian.randomusers.homefeature.domain.models.RandomUser
import com.hivian.randomusers.homefeature.domain.services.IRandomUsersService

internal class RandomUsersService(
    private val database: IDatabaseService,
    private val httpClient: IApiService
): IRandomUsersService {

    override suspend fun getUserById(userId: Int, imageSize: ImageSize): ServiceResult<RandomUser> {
        return runCatching {
            ServiceResult.Success(database.getUserById(userId).mapToRandomUser(imageSize))
        }.getOrDefault(
            ServiceResult.Error(ErrorType.DATABASE_ERROR)
        )
    }

    override suspend fun fetchRandomUsers(pageIndex: Int, pageSize: Int): ServiceResult<List<RandomUser>> {
        val httpUsersResult = httpClient.fetchRandomUsers(pageIndex, pageSize)
        val databaseUserResult = fetchDatabaseUsers(pageIndex, pageSize)

        return when (httpUsersResult) {
            is HttpResult.Success -> {
                val users = httpUsersResult.data.results

                if (users.isEmpty()) {
                    ServiceResult.Error(ErrorType.NO_RESULT, databaseUserResult)
                } else {
                    database.upsertUsers(users)
                    ServiceResult.Success(fetchDatabaseUsers(pageIndex, pageSize))
                }
            }
            is HttpResult.Error -> {
                ServiceResult.Error(httpUsersResult.errorType, databaseUserResult)
            }
        }
    }

    private suspend fun fetchDatabaseUsers(pageIndex: Int, pageSize: Int): List<RandomUser> {
        return database
            .fetchUsers(pageIndex, pageSize)
            .mapToRandomUsers(com.hivian.randomusers.homefeature.data.mappers.ImageSize.MEDIUM)
    }

}
