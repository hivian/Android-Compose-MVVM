package com.hivian.randomusers.core.domain.base

import com.hivian.randomusers.core.data.ServiceResult
import com.hivian.randomusers.core.data.remote.ErrorType

abstract class PaginationViewModel<Key, Item>(
    private val initialKey: Key,
    private val pageSize: Int
): ViewModelBase() {

    private var initialLoad: Boolean = true

    private var isLoading = false

    protected abstract fun getNextKey(currentKey: Key): Key

    protected abstract suspend fun onRequest(nextKey: Key, pageSize: Int) : ServiceResult<List<Item>>

    protected abstract fun onLoading(initialLoad: Boolean)

    protected abstract fun onError(errorType: ErrorType, users: List<Item>, initialLoad: Boolean)

    protected abstract fun onSuccess(users: List<Item>, initialLoad: Boolean)

    protected var currentKey: Key = initialKey
        private set

    protected suspend fun loadNextItems() {
        if (isLoading) return

        isLoading = true
        onLoading(initialLoad)

        when (val result = onRequest(currentKey, pageSize)) {
            is ServiceResult.Success -> {
                val items = result.data
                onSuccess(items, initialLoad)
                currentKey = getNextKey(currentKey)
            }
            is ServiceResult.Error -> {
                if (!result.data.isNullOrEmpty()) {
                    currentKey = getNextKey(currentKey)
                }

                onError(result.errorType, result.data ?: emptyList(), initialLoad)
            }
        }

        initialLoad = currentKey == initialKey
        isLoading = false
    }

    protected fun reset() {
        currentKey = initialKey
        initialLoad = true
    }

}