package com.weathertracker.root.repository

import com.weathertracker.root.model.User
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class UserRepository(
    private val sessionFactory: SessionFactory,
) {
    fun getAll(): List<User> = sessionFactory.currentSession.createQuery("from User", User::class.java).resultList

    @Transactional
    fun save(entity: User): User = sessionFactory.currentSession.persist(entity).let { entity }

    fun findById(id: Int?): User? = sessionFactory.currentSession.get(User::class.java, id)

    @Transactional
    fun delete(entity: User) = sessionFactory.currentSession.remove(entity)

    fun findByLoginAndPassword(
        login: String?,
        password: String?,
    ): User? =
        sessionFactory.currentSession
            .createQuery("from User u where u.login = :login and u.password = :password", User::class.java)
            .setParameter("login", login)
            .setParameter("password", password)
            .uniqueResult()
}
