package com.hivian.compose_mvvm.domain.services

import com.hivian.compose_mvvm.data.sources.models.RandomUserDTO

interface IDatabaseService {

    suspend fun getUserById(userId: Int): RandomUserDTO

    suspend fun fetchUsers(pageIndex: Int, pageSize: Int): List<RandomUserDTO>

    suspend fun upsertUsers(users : List<RandomUserDTO>)

}
