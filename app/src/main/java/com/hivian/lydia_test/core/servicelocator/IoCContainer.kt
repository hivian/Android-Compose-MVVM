package com.hivian.lydia_test.core.servicelocator

import kotlin.reflect.KClass

@PublishedApi
internal class IoCContainer {

    //region - Private Members

    private val registrations = mutableMapOf<Identifier, Lazy<*>>()

    //endregion

    //region - Internal Methods

    fun <T> register(named: String = "", ofClass: KClass<*>, instance: () -> T): Identifier = synchronized(this) {
        val identifier = Identifier(named, ofClass)
        val lazyInstance = Factory(instance)
        updateRegistrations(identifier, lazyInstance)
        identifier
    }

    fun <T> registerSingleton(named: String = "", ofClass: KClass<*>, instance: () -> T): Identifier = synchronized(this) {
        val identifier = Identifier(named, ofClass)
        val lazyInstance = lazy(this, instance)
        updateRegistrations(identifier, lazyInstance)
        identifier
    }

    fun reset() = synchronized(this) {
        registrations.clear()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> resolve(named: String = "", ofClass: KClass<*>): T {
        val identifier = Identifier(named, ofClass)
        val registration = checkNotNull(registrations[identifier]) {
            "${identifier.instance}" +
                    (if (identifier.named.isNotEmpty()) " named ${identifier.named}" else "") +
                    " is not registered in the IoC container"
        }
        return registration.value as T
    }

    //endregion

    //region - Private Methods

    private fun <T> updateRegistrations(identifier: Identifier, instance: Lazy<T>) {
        check(!registrations.containsKey(identifier)) {
            "Trying to inject 2 instances with the same definition"
        }
        registrations[identifier] = instance
    }

    //endregion

}
