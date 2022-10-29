package com.hivian.lydia_test.core.services.application

import androidx.lifecycle.LiveData
import com.hivian.lydia_test.core.models.dto.RandomUserDTO
import com.hivian.lydia_test.core.services.networking.HttpResult
import com.hivian.lydia_test.core.services.database.IDatabaseService
import com.hivian.lydia_test.core.services.networking.IHttpClient
import com.talentsoft.android.toolkit.core.IoC

internal class RandomUsersService: IRandomUsersService {

    private val database: IDatabaseService
        get() = IoC.resolve()

    private val httpClient: IHttpClient
        get() = IoC.resolve()

    val randomsUsersLocal: LiveData<List<RandomUserDTO>> = database.fetchAllUsers()

    override suspend fun fetchRandomUsers(page: Int, results: Int): HttpResult<List<RandomUserDTO>> {
        val httpResult = httpClient.fetchRandomUsers(page, results)

        if (httpResult is HttpResult.Success)
            saveRandomUsers(httpResult.data)

        return httpResult
    }

    private suspend fun saveRandomUsers(randomUserList: List<RandomUserDTO>) {
        database.upsertUsers(randomUserList)
    }

}
