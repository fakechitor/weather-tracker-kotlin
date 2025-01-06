package com.weathertracker.root.controller

import com.weathertracker.root.service.SessionService
import com.weathertracker.root.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping

// TODO rename
@Controller
class ViewController(
    private val userService: UserService,
    private val sessionService: SessionService,
) {
    @GetMapping
    fun index(
        @CookieValue(name = "session_id") sessionId: String,
        model: Model,
    ): String {
        model.addAttribute("username", userService.findById(sessionService.getSession(sessionId)?.user?.id)?.login)
        return "index"
    }

    @GetMapping("/sign_up")
    fun signUp(): String = "sign_up"

    @GetMapping("/login")
    fun login(): String = "login"
}
