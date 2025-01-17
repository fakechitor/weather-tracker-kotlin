package com.weathertracker.root.controller

import com.weathertracker.root.dto.LatitudeAndLongitudeDto
import com.weathertracker.root.service.LocationService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class WeatherController(
    private val locationService: LocationService,
) {
    @PostMapping("/weather")
    fun removeLocation(
        @ModelAttribute latitudeAndLongitudeDto: LatitudeAndLongitudeDto,
    ): String {
        locationService.deleteByLatitudeAndLongitude(latitudeAndLongitudeDto)
        return "redirect:/"
    }
}
