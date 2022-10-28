package com.hivian.lydia_test.business.repository

import androidx.lifecycle.LiveData
import com.hivian.lydia_test.business.model.dto.RandomUserDTO
import com.hivian.lydia_test.core.services.base.RepositoryBase
import com.hivian.lydia_test.core.services.networking.Resource
import com.hivian.lydia_test.core.services.database.IDatabaseService
import com.hivian.lydia_test.core.services.networking.IHttpClient
import com.talentsoft.android.toolkit.core.IoC

class RandomUsersRepository: RepositoryBase() {

    private val database: IDatabaseService
        get() = IoC.resolve()

    private val httpClient: IHttpClient
        get() = IoC.resolve()

    val randomsUsersLocal: LiveData<List<RandomUserDTO>> = database.fetchAllUsers()

    suspend fun fetchRandomUsers(page: Int, results: Int): Resource<List<RandomUserDTO>> {
        val safeCall = safeApiCall {
            httpClient.fetchRandomUsers(page, results)
        }
        if (safeCall is Resource.Success)
            saveRandomUsers(safeCall.data)
        return safeCall
    }

    private suspend fun saveRandomUsers(randomUserList: List<RandomUserDTO>) {
        database.upsertUsers(randomUserList)
    }

}
