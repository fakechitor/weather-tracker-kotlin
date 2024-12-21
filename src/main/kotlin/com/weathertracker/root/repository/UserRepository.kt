package com.weathertracker.root.repository

import com.weathertracker.root.model.User
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val sessionFactory: SessionFactory,
) : JpaRepository<User>() {
    override fun getAll(): List<User> {
        sessionFactory.openSession().use { session ->
            return session.createQuery("from User", User::class.java).resultList
        }
    }

    override fun findById(id: Long): User? =
        sessionFactory.openSession().use { session ->
            session
                .createQuery("from User u where u.id = id", User::class.java)
                .setParameter("id", id)
                .uniqueResult()
        }

    override fun save(entity: User): User {
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.persist(entity)
            session.transaction.commit()
        }
        return entity
    }
}
