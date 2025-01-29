package com.weathertracker.root.repository

import com.weathertracker.root.model.User
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val sessionFactory: SessionFactory,
) {
    fun save(entity: User): User =
        sessionFactory.currentSession
            .apply {
                persist(entity)
                flush()
            }.let { entity }

    fun findById(id: Int?): User? = sessionFactory.currentSession.get(User::class.java, id)

    fun findByLogin(login: String?): User? =
        sessionFactory.currentSession
            .createQuery("from User u where u.login = :login", User::class.java)
            .setParameter("login", login)
            .uniqueResult()
}
