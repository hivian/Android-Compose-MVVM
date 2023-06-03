package com.hivian_compose_mvvm.basic_feature.domain.services

import com.hivian.compose_mvvm.core.datasource.models.RandomUserDTO

interface IRandomUsersDatabaseService {

    suspend fun getUserById(userId: Int): RandomUserDTO

    suspend fun fetchUsers(pageIndex: Int, pageSize: Int): List<RandomUserDTO>

    suspend fun upsertUsers(users : List<RandomUserDTO>)

}
