package com.hivian_compose_mvvm.basic_feature.data.sources.local

import com.hivian.compose_mvvm.core.datasource.local.dao.IRandomUsersDao
import com.hivian.compose_mvvm.core.datasource.models.RandomUserDTO
import com.hivian_compose_mvvm.basic_feature.domain.services.IRandomUsersDatabaseService
import javax.inject.Inject

internal class RandomUsersDatabaseService @Inject constructor(
    private val dao: IRandomUsersDao
): IRandomUsersDatabaseService {

    override suspend fun getUserById(userId: Int): RandomUserDTO {
        return dao.getRandomUserById(userId)
    }

    override suspend fun fetchUsers(pageIndex: Int, pageSize: Int): List<RandomUserDTO> {
        return dao.getAllRandomUsers((pageIndex - 1) * pageSize, pageSize)
    }

    override suspend fun upsertUsers(users: List<RandomUserDTO>) {
        dao.upsert(users)
    }

}
