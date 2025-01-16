package com.weathertracker.root.service

import com.weathertracker.root.model.Location
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class OpenWeatherApiService(
    private val environment: Environment,
) {
    private val maxCitiesLimit = 100

    private val apiKey = environment.getProperty("weather.api.key")

    fun getJsonDataForAllCities(cityName: String): String? =
        getWebClient()
            .get()
            .uri("/geo/1.0/direct?q={city}&limit={limit}&appid={api}", cityName, maxCitiesLimit, apiKey)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()

    fun getJsonDataForLocationInfo(location: Location): String? =
        getWebClient()
            .get()
            .uri("/data/2.5/weather?lat={lat}&lon={lon}&appid={api}", location.latitude, location.longitude, apiKey)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()

    private fun getWebClient(): WebClient = WebClient.create("http://api.openweathermap.org")
}
