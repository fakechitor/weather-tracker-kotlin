package com.weathertracker.root.service

import com.weathertracker.root.dto.LocationDto
import com.weathertracker.root.dto.WeatherDeleteDto
import com.weathertracker.root.dto.mapper.LocationMapper
import com.weathertracker.root.exception.LocationAlreadyExistsException
import com.weathertracker.root.model.Location
import com.weathertracker.root.model.User
import com.weathertracker.root.repository.LocationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LocationService(
    private val locationRepository: LocationRepository,
    private val userService: UserService,
    private val locationMapper: LocationMapper,
) {
    @Transactional
    fun saveLocationForUser(
        locationDto: LocationDto,
        userId: Int,
    ) = runCatching {
        locationRepository.save(locationMapper.convertToModel(locationDto).apply { user = userService.findById(userId) })
    }.onFailure {
        throw LocationAlreadyExistsException("You already added this location")
    }

    @Transactional
    fun deleteById(weatherDeleteDto: WeatherDeleteDto) =
        locationRepository.deleteById(
            locationId = weatherDeleteDto.locationId,
            userId = weatherDeleteDto.userId,
        )

    fun getLocationsForUser(user: User?): List<Location> = locationRepository.getByUserId(user?.id)
}
