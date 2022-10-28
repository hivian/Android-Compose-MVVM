package com.hivian.lydia_test.business.remote

fun getRetrofitApiLayer(): HttpRetrofit = HttpRetrofit(
    RetrofitApiClient().createService(ApiService::class.java)
)
