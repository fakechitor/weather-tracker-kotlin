package com.weathertracker.root.repository

import com.weathertracker.root.model.Session
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository

@Repository
class SessionRepository(
    private val sessionFactory: SessionFactory,
) {
    fun save(entity: Session): Session = sessionFactory.currentSession.persist(entity).let { entity }

    fun getAll(): List<Session> = sessionFactory.currentSession.createQuery("FROM Session", Session::class.java).resultList

    fun findById(id: String): Session? = sessionFactory.currentSession.get(Session::class.java, id)

    fun delete(entity: Session) = sessionFactory.currentSession.remove(entity)

    fun deleteExpiredSessions() =
        sessionFactory.currentSession.createNativeMutationQuery("delete from sessions where expires_at < now()").executeUpdate()
}
