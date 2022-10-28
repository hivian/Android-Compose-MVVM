package com.hivian.lydia_test.core


interface IRestClient<T: Any> {

    suspend fun fetchRandomUsers(page: Int, results: Int): List<T>

}