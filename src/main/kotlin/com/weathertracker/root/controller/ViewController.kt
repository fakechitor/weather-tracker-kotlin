package com.weathertracker.root.controller

import com.weathertracker.root.service.SessionService
import com.weathertracker.root.service.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ViewController(
    private val sessionService: SessionService,
    private val userService: UserService,
) {
    @GetMapping
    fun index(
        model: Model,
        request: HttpServletRequest,
    ): String {
        val sessionId = sessionService.getSession(request.cookies?.find { it.name == "session_id" }?.value!!)
        model.addAttribute("isLogged", true)
        model.addAttribute("username", userService.findById(sessionId?.user?.id)?.login)
        return "index"
    }

    @GetMapping("/sign_up")
    fun signUp(): String = "sign_up"

    @GetMapping("/login")
    fun login(): String = "login"
}
