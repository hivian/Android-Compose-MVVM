package com.hivian.common


interface IRestClient<T: Any> {

    suspend fun fetchRandomUsers(page: Int, results: Int): List<T>

}