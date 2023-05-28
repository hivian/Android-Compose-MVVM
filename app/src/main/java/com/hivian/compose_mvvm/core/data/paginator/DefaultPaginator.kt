package com.hivian.compose_mvvm.core.data.paginator

import com.hivian.compose_mvvm.core.data.ServiceResult
import com.hivian.compose_mvvm.core.data.network.ErrorType

class DefaultPaginator<Key, Item>(

    private val initialKey: Key,

    private inline val getNextKey: suspend (currentKey: Key) -> Key,

    private inline val onRequest : suspend (nextKey: Key) -> ServiceResult<List<Item>>,

    private inline val onLoading: (initialLoad: Boolean) -> Unit,

    private inline val onError: suspend (errorType: ErrorType, users: List<Item>, initialLoad: Boolean) -> Unit,

    private inline val onSuccess: suspend (users: List<Item>, initialLoad: Boolean) -> Unit

): IPaginator<Key, Item> {

    override var currentKey: Key = initialKey
        private set

    private var initialLoad: Boolean = true

    private var isLoading = false

    override suspend fun loadNextItems() {
        if (isLoading) return

        isLoading = true
        onLoading(initialLoad)

        val result = onRequest(currentKey)

        when (result) {
            is ServiceResult.Success -> {
                val items = result.data
                onSuccess(items, initialLoad)
                currentKey = getNextKey(currentKey)
            }
            is ServiceResult.Error -> {
                if (result.data.isNotEmpty()) {
                    currentKey = getNextKey(currentKey)
                }

                onError(result.errorType, result.data, initialLoad)
            }
        }

        initialLoad = currentKey == initialKey
        isLoading = false
    }

    override fun reset() {
        currentKey = initialKey
        initialLoad = true
    }


}