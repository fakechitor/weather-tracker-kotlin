package com.weathertracker.root.controller

import com.weathertracker.root.dto.HttpContextDto
import com.weathertracker.root.dto.UserDto
import com.weathertracker.root.service.CookieService
import com.weathertracker.root.service.SessionService
import com.weathertracker.root.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class AuthController(
    private val sessionService: SessionService,
    private val userService: UserService,
    private val cookieService: CookieService,
) {
    @PostMapping("/sign_out")
    fun signOut(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): String {
        cookieService.removeCookie(response)
        return "redirect:/login"
    }

    @PostMapping("/login")
    fun loginUser(
        @RequestParam username: String,
        @RequestParam password: String,
        model: Model,
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): String {
        val userDto = UserDto(login = username, password = password)
        if (userService.isUserExist(userDto)) {
            model.addAttribute("isLogged", true)
            model.addAttribute("username", username)
            sessionService.createSessionIfNotExist(HttpContextDto(request, response, userDto))
            return "redirect:/"
        }
        // TODO make error message
        return "login"
    }

    @PostMapping("/sign_up")
    fun signUpUser(
        @RequestParam username: String,
        @RequestParam password: String,
        @RequestParam confirmPassword: String,
        request: HttpServletRequest,
        response: HttpServletResponse,
        model: Model,
    ): String {
        // TODO passwords should be equal
        val userDto = UserDto(login = username, password = password)
        userService.saveUser(userDto)
        sessionService.createSessionIfNotExist(HttpContextDto(request = request, response = response, userDto = userDto))
        return "redirect:/"
    }
}
