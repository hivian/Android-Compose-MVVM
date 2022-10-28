package com.hivian.lydia_test.core.servicelocator

internal class Factory<out T> constructor(private val initializer: () -> T) : Lazy<T> {

    //region - Lazy Implementation

    override val value: T
        get() {
            return initializer()
        }

    override fun isInitialized(): Boolean = false

    //endregion
}
