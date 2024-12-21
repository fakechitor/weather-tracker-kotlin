package com.weathertracker.root.repository

abstract class JpaRepository<T> {
    abstract fun save(entity: T): T

    abstract fun getAll(): List<T>

    abstract fun findById(id: Long): T?
}
