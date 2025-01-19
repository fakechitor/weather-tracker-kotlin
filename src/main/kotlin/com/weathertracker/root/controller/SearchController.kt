package com.weathertracker.root.controller

import com.weathertracker.root.dto.CitySearchDto
import com.weathertracker.root.dto.LocationDto
import com.weathertracker.root.service.AuthService
import com.weathertracker.root.service.LocationService
import com.weathertracker.root.service.WeatherService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*

@Controller
class SearchController(
    private val weatherService: WeatherService,
    private val locationService: LocationService,
    private val authService: AuthService,
) {
    @GetMapping("/search")
    fun get(
        @Valid @ModelAttribute citySearchDto: CitySearchDto,
        @CookieValue(name = "session_id") sessionId: String,
        model: Model,
    ): String {
        model["user"] = authService.getUserIfAuthorized(sessionId)
        model["location"] = weatherService.getWeatherInfoForCitiesByCityName(citySearchDto.city)
        model["query"] = citySearchDto.city
        return "search"
    }

    @PostMapping("/search")
    fun saveLocation(
        @RequestParam userId: Int,
        @ModelAttribute locationDto: LocationDto,
    ): String {
        locationService.saveLocationForUser(locationDto = locationDto, userId = userId)
        return "redirect:/"
    }
}
