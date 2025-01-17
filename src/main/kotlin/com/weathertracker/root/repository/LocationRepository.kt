package com.weathertracker.root.repository

import com.weathertracker.root.model.Location
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class LocationRepository(
    private val sessionFactory: SessionFactory,
) {
    fun save(location: Location) = sessionFactory.currentSession.persist(location).let { location }

    fun delete(location: Location) = sessionFactory.currentSession.remove(location)

    fun deleteByLatitudeAndLongitudeAndUserId(
        latitude: Double,
        longitude: Double,
        userId: Int,
    ) = sessionFactory.currentSession
        .createMutationQuery("DELETE FROM Location l WHERE l.latitude = :lat AND l.longitude = :lon and l.user.id = :userId")
        .setParameter("userId", userId)
        .setParameter("lat", latitude)
        .setParameter("lon", longitude)
        .executeUpdate()

    fun getByUserId(id: Int?): List<Location> =
        sessionFactory.currentSession
            .createQuery("FROM Location l WHERE l.user.id = :userId", Location::class.java)
            .setParameter("userId", id)
            .resultList
}
