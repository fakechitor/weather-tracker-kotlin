package com.weathertracker.root.service

import com.weathertracker.root.dto.LoginUserDto
import com.weathertracker.root.dto.SessionInfoDto
import com.weathertracker.root.dto.SignupUserDto
import com.weathertracker.root.exception.UserNotFoundException
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userService: UserService,
    private val sessionService: SessionService,
    private val cookieService: CookieService,
) {
    fun register(
        sessionInfoDto: SessionInfoDto,
        signupUserDto: SignupUserDto,
    ) {
        // TODO make passwords mask
        throwIfPasswordsNotEqual(signupUserDto.password, signupUserDto.confirmPassword)
        sessionService.createSession(
            sessionInfoDto = sessionInfoDto,
            user = userService.saveUser(LoginUserDto(username = signupUserDto.username, password = signupUserDto.password)),
        )
    }

    fun logout(
        response: HttpServletResponse,
        sessionId: String,
    ) {
        cookieService.removeCookie(response)
        sessionService.deleteSessionById(sessionId)
    }

    fun tryAuthenticateAndCreateSession(
        loginUserDto: LoginUserDto,
        sessionInfoDto: SessionInfoDto,
    ) {
        userService.findByLoginAndPassword(loginUserDto)?.let { user ->
            sessionService.createSession(sessionInfoDto, user)
        } ?: throw UserNotFoundException("User not found")
    }

    private fun throwIfPasswordsNotEqual(
        password: String?,
        confirmPassword: String?,
    ) {
        if (password != confirmPassword) throw IllegalArgumentException("Passwords don't match")
    }
}
