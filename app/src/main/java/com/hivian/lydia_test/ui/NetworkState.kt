package com.hivian.lydia_test.ui

sealed class NetworkState {
    object Loading: NetworkState()
    data class Error(val message: String): NetworkState()
    object Success: NetworkState()

    fun isLoading() = this is Loading
    fun isError() = this is Error
    fun isSuccess() = this is Success
}
