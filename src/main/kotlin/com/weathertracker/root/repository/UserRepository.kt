package com.weathertracker.root.repository

import com.weathertracker.root.model.User
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class UserRepository(
    private val sessionFactory: SessionFactory,
) {
    fun getAll(): List<User> = sessionFactory.currentSession.createQuery("from User", User::class.java).resultList

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

    fun delete(entity: User) = sessionFactory.currentSession.remove(entity)
}
