package com.weathertracker.root.repository

import com.weathertracker.root.model.Location
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository

@Repository
class LocationRepository(
    private val sessionFactory: SessionFactory,
) {
    fun save(location: Location) = sessionFactory.currentSession.persist(location).let { location }

    fun deleteById(
        locationId: Int?,
        userId: Int,
    ) = sessionFactory.currentSession
        .createMutationQuery("DELETE FROM Location l WHERE l.id = :id and l.user.id = :userId")
        .setParameter("userId", userId)
        .setParameter("id", locationId)
        .executeUpdate()

    fun getByUserId(id: Int?): List<Location> =
        sessionFactory.currentSession
            .createQuery("FROM Location l WHERE l.user.id = :userId", Location::class.java)
            .setParameter("userId", id)
            .resultList
}
