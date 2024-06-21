package com.hivian_compose_mvvm.basic_feature.data.repository

import com.hivian.compose_mvvm.core.datasources.remote.ErrorType
import com.hivian.compose_mvvm.core.datasources.remote.HttpResult
import com.hivian.compose_mvvm.core.datasources.ServiceResult
import com.hivian_compose_mvvm.basic_feature.data.mappers.ImageSize
import com.hivian_compose_mvvm.basic_feature.data.mappers.mapToRandomUser
import com.hivian_compose_mvvm.basic_feature.data.mappers.mapToRandomUsers
import com.hivian_compose_mvvm.basic_feature.domain.models.RandomUser
import com.hivian_compose_mvvm.basic_feature.domain.repository.IRandomUsersRepository
import com.hivian_compose_mvvm.basic_feature.domain.services.IRandomUsersDatabaseService
import com.hivian_compose_mvvm.basic_feature.domain.services.IRandomUsersHttpService

internal class RandomUsersRepository(
    private val database: IRandomUsersDatabaseService,
    private val httpClient: IRandomUsersHttpService
): IRandomUsersRepository {

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
            .mapToRandomUsers(ImageSize.MEDIUM)
    }

}
