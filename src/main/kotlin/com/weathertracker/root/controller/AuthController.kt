package com.weathertracker.root.controller

import com.weathertracker.root.dto.LoginUserDto
import com.weathertracker.root.dto.SessionInfoDto
import com.weathertracker.root.dto.SignupUserDto
import com.weathertracker.root.service.AuthService
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/logout")
    fun signOut(
        @CookieValue(name = "session_id") sessionId: String,
        response: HttpServletResponse,
    ): String {
        authService.logout(response, sessionId)
        return "redirect:/login"
    }

    @PostMapping("/login")
    fun loginUser(
        @Valid @ModelAttribute("loginUserDto") loginUserDto: LoginUserDto,
        @CookieValue(name = "session_id", defaultValue = "") sessionId: String,
        bindingResult: BindingResult,
        response: HttpServletResponse,
        model: Model,
    ): String {
        authService.tryAuthenticateAndCreateSession(loginUserDto, SessionInfoDto(sessionId = sessionId, response = response))
        model["username"] = loginUserDto.username
        return "redirect:/"
    }

    @PostMapping("/sign_up")
    fun signUpUser(
        @Valid @ModelAttribute("signupUserDto") signupUserDto: SignupUserDto,
        @CookieValue(name = "session_id", defaultValue = "") sessionId: String,
        bindingResult: BindingResult,
        response: HttpServletResponse,
        model: Model,
    ): String {
        authService.register(
            SessionInfoDto(
                sessionId = sessionId,
                response = response,
            ),
            signupUserDto = signupUserDto,
        )
        return "redirect:/"
    }
}
