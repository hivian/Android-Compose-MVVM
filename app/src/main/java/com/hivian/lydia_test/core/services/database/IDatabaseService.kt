package com.hivian.lydia_test.core.services.database

import androidx.lifecycle.LiveData
import com.hivian.lydia_test.business.model.dto.RandomUserDTO

interface IDatabaseService {

    fun fetchAllUsers(): LiveData<List<RandomUserDTO>>

    suspend fun upsertUsers(users : List<RandomUserDTO>)

}