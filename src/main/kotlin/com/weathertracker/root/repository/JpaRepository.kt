package com.weathertracker.root.repository

abstract class JpaRepository<T> {
    abstract fun save(entity: T): T

    abstract fun getAll(): List<T>

    protected fun <T> execute(): T {
        TODO()
    }
}
