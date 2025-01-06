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

    private fun throwIfPasswordsNotEqual(
        password: String?,
        confirmPassword: String?,
    ) {
        if (password != confirmPassword) throw IllegalArgumentException("Passwords don't match")
    }

    fun tryAuthenticateAndCreateSession(
        loginUserDto: LoginUserDto,
        sessionInfoDto: SessionInfoDto,
    ): Boolean {
        if (userService.isUserExist(loginUserDto)) {
            sessionService.createSession(sessionInfoDto = sessionInfoDto, user = userService.findByLoginAndPassword(loginUserDto))
            return true
        }
        throw UserNotFoundException("User not found")
    }
}
