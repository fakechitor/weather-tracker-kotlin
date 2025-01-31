package com.weathertracker.root.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.weathertracker.root.dto.LocationInfoDto
import com.weathertracker.root.dto.mapper.LocationSearchResponseDto
import com.weathertracker.root.exception.OpenWeatherApiException
import com.weathertracker.root.model.Location
import com.weathertracker.root.model.User
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class WeatherService(
    private val jacksonMapper: ObjectMapper,
    private val openWeatherApiService: OpenWeatherApiService,
    private val locationService: LocationService,
) {
    fun getWeatherInfoForAuthorizedUser(user: User?) =
        locationService.getLocationsForUser(user).map {
            getLocationInfoOrThrow(it)
        }

    fun getAllLocationsByCityName(cityName: String): List<LocationSearchResponseDto> =
        jacksonMapper.readValue(
            openWeatherApiService.getJsonDataForAllCities(cityName),
            object : TypeReference<List<LocationSearchResponseDto>>() {},
        )

    private fun getLocationInfoOrThrow(location: Location): LocationInfoDto {
        val jsonNode = jacksonMapper.readTree(openWeatherApiService.getJsonDataForLocationInfo(location))
        return when (HttpStatus.resolve(jsonNode.get("cod").asInt())) {
            HttpStatus.BAD_REQUEST -> throw OpenWeatherApiException("Bad request")
            HttpStatus.UNAUTHORIZED -> throw OpenWeatherApiException("Unauthorized")
            HttpStatus.NOT_FOUND -> throw OpenWeatherApiException("Not found")
            HttpStatus.OK ->
                LocationInfoDto(
                    id = location.id,
                    latitude = jsonNode.path("coord").path("lat").asDouble(),
                    longitude = jsonNode.path("coord").path("lon").asDouble(),
                    city = location.name.toString(),
                    country = jsonNode.path("sys").path("country").asText(),
                    temperature = jsonNode.path("main").path("temp").asDouble(),
                    temperatureFeels = jsonNode.path("main").path("feels_like").asDouble(),
                    humidity = jsonNode.path("main").path("humidity").asInt(),
                    iconCode = jsonNode.path("weather").get(0).path("icon").asText(),
                )
            else -> throw OpenWeatherApiException("Internal Server Error")
        }
    }
}
