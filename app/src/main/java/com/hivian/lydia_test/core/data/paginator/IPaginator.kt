package com.hivian.lydia_test.core.data.paginator

interface IPaginator<Key, Item> {

    suspend fun loadNextItems()

    fun reset()

}
