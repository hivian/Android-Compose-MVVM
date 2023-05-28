package com.hivian.compose_mvvm.core.data.paginator

interface IPaginator<Key, Item> {

    val currentKey: Key

    suspend fun loadNextItems()

    fun reset()

}
