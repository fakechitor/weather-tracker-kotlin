package com.weathertracker.root.controller

import com.weathertracker.root.dto.UserDto
import com.weathertracker.root.service.LoginService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/login")
class LoginController(
    private val loginService: LoginService,
) {
    @GetMapping("")
    fun getLoginPage(): String = "login"

    @PostMapping("")
    fun loginUser(
        @RequestParam login: String,
        @RequestParam password: String,
    ): String {
        loginService.saveUser(UserDto(login = login, password = password))
        return "login_success"
    }
}
