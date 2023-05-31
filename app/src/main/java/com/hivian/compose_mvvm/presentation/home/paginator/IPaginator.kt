package com.hivian.compose_mvvm.presentation.home.paginator

interface IPaginator<Key, Item> {

    val currentKey: Key

    suspend fun loadNextItems()

    fun reset()

}
