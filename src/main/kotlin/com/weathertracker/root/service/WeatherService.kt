package com.weathertracker.root.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.weathertracker.root.dto.LocationDto
import com.weathertracker.root.dto.LocationInfoDto
import com.weathertracker.root.dto.mapper.LocationMapper
import com.weathertracker.root.exception.OpenWeatherApiException
import com.weathertracker.root.model.Location
import com.weathertracker.root.util.HttpCode
import org.springframework.stereotype.Service

@Service
class WeatherService(
    private val jacksonMapper: ObjectMapper,
    private val locationMapper: LocationMapper,
    private val openWeatherApiService: OpenWeatherApiService,
) {
    fun getWeatherInfoForCitiesByCityName(cityName: String): List<LocationInfoDto> =
        getAllLocationsByCityName(cityName).map {
            getLocationInfoOrThrow(locationMapper.convertToModel(it))
        }

    fun getAllLocationsByCityName(cityName: String): List<LocationDto> =
        jacksonMapper.readValue(
            openWeatherApiService.getJsonDataForAllCities(cityName),
            object : TypeReference<List<LocationDto>>() {},
        )

    fun getLocationInfoOrThrow(location: Location): LocationInfoDto {
        val jsonNode = jacksonMapper.readTree(openWeatherApiService.getJsonDataForLocationInfo(location))
        return when (HttpCode.fromCode(jsonNode.path("cod").asInt())) {
            HttpCode.BAD_REQUEST -> throw OpenWeatherApiException("Bad request")
            HttpCode.UNAUTHORIZED -> throw OpenWeatherApiException("Unauthorized")
            HttpCode.NOT_FOUND -> throw OpenWeatherApiException("Not found")
            HttpCode.INTERNAL_SERVER_ERROR -> throw OpenWeatherApiException("Internal Server Error")
            HttpCode.OK ->
                LocationInfoDto(
                    city = jsonNode.path("name").asText(),
                    country = jsonNode.path("sys").path("country").asText(),
                    temperature = jsonNode.path("main").path("temp").asDouble(),
                    humidity = jsonNode.path("main").path("humidity").asInt(),
                    iconCode =
                        jsonNode
                            .path("weather")
                            .get(0)
                            .path("icon")
                            .asText(),
                )
        }
    }
}
