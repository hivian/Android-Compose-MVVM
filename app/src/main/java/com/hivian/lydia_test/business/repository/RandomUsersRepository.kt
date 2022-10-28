package com.hivian.lydia_test.business.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.hivian.lydia_test.core.IRestClient
import com.hivian.lydia_test.business.db.RandomUsersDatabase
import com.hivian.lydia_test.business.model.dto.RandomUserDTO
import com.hivian.lydia_test.core.BaseRepository
import com.hivian.lydia_test.core.Resource

class RandomUsersRepository(application: Application, private val restClient: IRestClient<RandomUserDTO>): BaseRepository() {

    private val randomsUsersDao = RandomUsersDatabase.getInstance(application).randomUsersDao()

    val randomsUsersLocal: LiveData<List<RandomUserDTO>> = randomsUsersDao.getAllRandomUsers()

    suspend fun fetchRandomUsers(page: Int, results: Int): Resource<List<RandomUserDTO>> {
        val safeCall = safeApiCall {
            restClient.fetchRandomUsers(page, results)
        }
        if (safeCall is Resource.Success)
            saveRandomUsers(safeCall.data)
        return safeCall
    }

    private suspend fun saveRandomUsers(randomUserList: List<RandomUserDTO>) {
        randomsUsersDao.upsert(randomUserList)
    }

}
