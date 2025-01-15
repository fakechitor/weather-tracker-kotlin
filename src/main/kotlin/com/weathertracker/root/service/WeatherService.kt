package com.weathertracker.root.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.weathertracker.root.dto.LocationDto
import com.weathertracker.root.dto.LocationInfoDto
import com.weathertracker.root.dto.mapper.LocationMapper
import com.weathertracker.root.model.Location
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class WeatherService(
    private val jacksonMapper: ObjectMapper,
    private val locationMapper: LocationMapper,
    private val environment: Environment,
) {
    private val maxCitiesLimit = 100
    private val apiKey = environment.getProperty("weather.api.key")

    fun getAllLocationsByCityName(cityName: String): List<LocationDto> =
        jacksonMapper.readValue(
            getWebClient()
                .get()
                .uri("/geo/1.0/direct?q={city}&limit={limit}&appid={api}", cityName, maxCitiesLimit, apiKey)
                .retrieve()
                .bodyToMono(String::class.java)
                .block(),
            object : TypeReference<List<LocationDto>>() {},
        )

    private fun getWebClient(): WebClient = WebClient.create("http://api.openweathermap.org")

    fun getWeatherInfoByLocation(location: Location): LocationInfoDto {
        val jsonNode: JsonNode =
            jacksonMapper.readTree(
                getWebClient()
                    .get()
                    .uri("/data/2.5/weather?q={city}&appid={api}", location.name, apiKey)
                    .retrieve()
                    .bodyToMono(String::class.java)
                    .block(),
            )
        return LocationInfoDto(
            city = jsonNode.path("name").asText(),
            country = jsonNode.path("sys").path("country").asText(),
            temperature = jsonNode.path("main").path("temp").asDouble(),
            iconCode =
                jsonNode
                    .path("weather")
                    .get(0)
                    .path("icon")
                    .asText(),
        )
    }

    fun getWeatherInfoForCitiesByCityName(cityName: String): List<LocationInfoDto> =
        getAllLocationsByCityName(cityName).map { getWeatherInfoByLocation(locationMapper.convertToModel(it)) }
}
