package com.weathertracker.root.service

import com.weathertracker.root.model.Location
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class OpenWeatherApiService(
    private val environment: Environment,
    private val webClient: WebClient,
) {
    private val maxCitiesLimit = 100

    private val apiKey = environment.getProperty("weather.api.key")

    fun getJsonDataForAllCities(cityName: String) =
        webClient
            .get()
            .uri("/geo/1.0/direct?q={city}&limit={limit}&appid={api}", cityName, maxCitiesLimit, apiKey)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()

    fun getJsonDataForLocationInfo(location: Location) =
        webClient
            .get()
            .uri(
                "/data/2.5/weather?lat={lat}&lon={lon}&appid={api}&units=metric",
                location.latitude,
                location.longitude,
                apiKey,
            ).retrieve()
            .bodyToMono(String::class.java)
            .block()
}
