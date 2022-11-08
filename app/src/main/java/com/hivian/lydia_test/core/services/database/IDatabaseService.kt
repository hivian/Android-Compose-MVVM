package com.hivian.lydia_test.core.services.database

import com.hivian.lydia_test.core.models.dto.RandomUserDTO

interface IDatabaseService {

    suspend fun getUserById(userId: Int): RandomUserDTO

    suspend fun fetchUsers(): List<RandomUserDTO>

    suspend fun upsertUsers(users : List<RandomUserDTO>)

}