package com.hivian.lydia_test.business.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.hivian.common.IRestClient
import com.hivian.lydia_test.business.db.RandomUsersDatabase
import com.hivian.lydia_test.business.model.dto.RandomUserDTO

class RandomUsersRepository(application: Application, private val restClient: IRestClient<RandomUserDTO>): com.hivian.common.BaseRepository() {

    private val randomsUsersDao = RandomUsersDatabase.getInstance(application).randomUsersDao()

    val randomsUsersLocal: LiveData<List<RandomUserDTO>> = randomsUsersDao.getAllRandomUsers()

    suspend fun fetchRandomUsers(page: Int, results: Int): com.hivian.common.Resource<List<RandomUserDTO>> {
        val safeCall = safeApiCall {
            restClient.fetchRandomUsers(page, results)
        }
        if (safeCall is com.hivian.common.Resource.Success)
            saveRandomUsers(safeCall.data)
        return safeCall
    }

    private suspend fun saveRandomUsers(randomUserList: List<RandomUserDTO>) {
        randomsUsersDao.upsert(randomUserList)
    }

}
