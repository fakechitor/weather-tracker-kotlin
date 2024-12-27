package com.weathertracker.root.repository

import com.weathertracker.root.model.User
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val sessionFactory: SessionFactory,
) : JpaRepository<User> {
    override fun getAll(): List<User> {
        sessionFactory.openSession().use { session ->
            return session.createQuery("from User", User::class.java).resultList
        }
    }

    override fun save(entity: User): User {
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.persist(entity)
            session.transaction.commit()
        }
        return entity
    }

    override fun <Int> findById(id: Int): User? =
        sessionFactory.openSession().use { session ->
            session
                .createQuery("from User u where u.id = :id", User::class.java)
                .setParameter("id", id)
                .uniqueResult()
        }

    override fun delete(entity: User) {
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.remove(entity)
            session.transaction.commit()
        }
    }

    fun findByLoginAndPassword(
        login: String?,
        password: String?,
    ): User? =
        sessionFactory.openSession().use { session ->
            session
                .createQuery("from User u where u.login = :login and u.password = :password", User::class.java)
                .setParameter("login", login)
                .setParameter("password", password)
                .uniqueResult()
        }
}
