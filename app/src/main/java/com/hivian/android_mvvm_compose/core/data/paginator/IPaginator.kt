package com.hivian.android_mvvm_compose.core.data.paginator

interface IPaginator<Key, Item> {

    val currentKey: Key

    suspend fun loadNextItems()

    fun reset()

}
