package com.weathertracker.root.service

import com.weathertracker.root.dto.LatitudeAndLongitudeDto
import com.weathertracker.root.dto.LocationDto
import com.weathertracker.root.dto.mapper.LocationMapper
import com.weathertracker.root.exception.LocationAlreadyExistsException
import com.weathertracker.root.model.Location
import com.weathertracker.root.model.User
import com.weathertracker.root.repository.LocationRepository
import org.hibernate.exception.ConstraintViolationException
import org.springframework.stereotype.Service

@Service
class LocationService(
    private val locationRepository: LocationRepository,
    private val userService: UserService,
    private val locationMapper: LocationMapper,
) {
    fun saveLocationForUser(
        locationDto: LocationDto,
        userId: Int,
    ) = try {
        locationRepository.save(locationMapper.convertToModel(locationDto).apply { user = userService.findById(userId) })
    } catch (e: ConstraintViolationException) {
        throw LocationAlreadyExistsException("You already added this location")
    }

    fun deleteByLatitudeAndLongitude(latitudeAndLongitudeDto: LatitudeAndLongitudeDto) =
        locationRepository.deleteByLatitudeAndLongitude(
            latitude = latitudeAndLongitudeDto.latitude,
            longitude = latitudeAndLongitudeDto.longitude,
        )

    fun getLocationsForUser(user: User?): List<Location> = locationRepository.getByUserId(user?.id)
}
