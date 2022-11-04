package com.hivian.lydia_test.core.servicelocator

object IoC {

    //region - Internal Members

    @PublishedApi
    internal val container = IoCContainer()

    //endregion

    //region - Public Methods

    inline fun <reified T> register(named: String = "", noinline instance: () -> T): Identifier =
        container.register(named, T::class, instance)

    inline fun <reified T> registerSingleton(named: String = "", noinline instance: () -> T): Identifier =
        container.registerSingleton(named, T::class, instance)

    fun reset() = container.reset()

    inline fun <reified T> resolve(named: String = ""): T = container.resolve(named, T::class)

    //endregion

}
