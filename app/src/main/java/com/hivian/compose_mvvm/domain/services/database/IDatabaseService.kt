package com.hivian.compose_mvvm.domain.services.database

import com.hivian.compose_mvvm.data.models.RandomUserDTO

interface IDatabaseService {

    suspend fun getUserById(userId: Int): RandomUserDTO

    suspend fun fetchUsers(pageIndex: Int, pageSize: Int): List<RandomUserDTO>

    suspend fun upsertUsers(users : List<RandomUserDTO>)

}
