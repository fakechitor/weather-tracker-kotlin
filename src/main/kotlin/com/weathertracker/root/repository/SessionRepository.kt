package com.weathertracker.root.repository

import com.weathertracker.root.model.Session
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository

@Repository
class SessionRepository(
    private val sessionFactory: SessionFactory,
) : JpaRepository<Session>() {
    override fun save(entity: Session): Session =
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.persist(session)
            session.transaction.commit()
            entity
        }

    override fun getAll(): List<Session> =
        sessionFactory.openSession().use { session ->
            session.createQuery("from Session", Session::class.java).resultList
        }

    override fun findById(id: Long): Session? =
        sessionFactory.openSession().use { session ->
            session
                .createQuery("from Session s where s.id = :id", Session::class.java)
                .setParameter("id", id)
                .uniqueResult()
        }
}
