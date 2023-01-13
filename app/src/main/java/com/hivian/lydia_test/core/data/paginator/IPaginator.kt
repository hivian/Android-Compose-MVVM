package com.hivian.lydia_test.core.data.paginator

interface IPaginator<Key, Item> {

    val currentKey: Key

    suspend fun loadNextItems()

    fun reset()

}
