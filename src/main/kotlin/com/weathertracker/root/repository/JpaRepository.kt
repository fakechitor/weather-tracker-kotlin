package com.weathertracker.root.repository

interface JpaRepository<T> {
    fun save(entity: T): T

    fun getAll(): List<T>

    fun delete(entity: T)

    fun <A> findById(id: A): T?
}
