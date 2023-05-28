package com.hivian.lydia_test.domain.services.database

import com.hivian.lydia_test.data.models.RandomUserDTO

interface IDatabaseService {

    suspend fun getUserById(userId: Int): RandomUserDTO

    suspend fun fetchUsers(pageIndex: Int, pageSize: Int): List<RandomUserDTO>

    suspend fun upsertUsers(users : List<RandomUserDTO>)

}
