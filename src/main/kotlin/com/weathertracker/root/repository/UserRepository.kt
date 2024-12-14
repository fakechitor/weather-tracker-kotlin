package com.weathertracker.root.repository

import com.weathertracker.root.model.User

class UserRepository : JpaRepository<User>() {
    override fun getAll(): List<User> {
        TODO("Not yet implemented")
    }

    override fun save(model: User): User {
        TODO()
    }
}
