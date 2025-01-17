package com.weathertracker.root.controller

import com.weathertracker.root.service.AuthService
import com.weathertracker.root.service.WeatherService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PageController(
    private val authService: AuthService,
    private val weatherService: WeatherService,
) {
    @GetMapping
    fun index(
        @CookieValue(name = "session_id") sessionId: String,
        model: Model,
    ): String {
        authService.getUserIfAuthorized(sessionId).also { model["user"] = it }.also {
            model["location"] = weatherService.getWeatherInfoForAuthorizedUser(it)
        }
        return "index"
    }

    @GetMapping("/sign_up")
    fun signUp(): String = "sign_up"

    @GetMapping("/login")
    fun login(): String = "login"
}
