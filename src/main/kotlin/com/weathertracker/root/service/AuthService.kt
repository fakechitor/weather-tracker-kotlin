package com.weathertracker.root.service

import at.favre.lib.crypto.bcrypt.BCrypt
import com.weathertracker.root.dto.LoginUserDto
import com.weathertracker.root.dto.SessionInfoDto
import com.weathertracker.root.dto.SignupUserDto
import com.weathertracker.root.exception.IncorrectPasswordException
import com.weathertracker.root.exception.PasswordMismatchException
import com.weathertracker.root.exception.UserNotFoundException
import com.weathertracker.root.model.User
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
        // TODO try to move validation
        throwIfPasswordsNotEqual(signupUserDto.password, signupUserDto.confirmPassword)
        sessionService.createSessionAndAddCookie(
            sessionInfoDto = sessionInfoDto,
            user = userService.saveUser(LoginUserDto(username = signupUserDto.username, password = encryptPassword(signupUserDto))),
        )
    }

    private fun encryptPassword(signupUserDto: SignupUserDto): String =
        BCrypt.withDefaults().hashToString(10, signupUserDto.password?.toCharArray())

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
        userService.findByLogin(loginUserDto)?.let { user ->
            sessionService.createSessionAndAddCookie(sessionInfoDto, user).takeIf { isPasswordValid(loginUserDto, user) }
                ?: throw IncorrectPasswordException("Password is incorrect")
        } ?: throw UserNotFoundException("User not found")
    }

    private fun isPasswordValid(
        loginUserDto: LoginUserDto,
        user: User,
    ): Boolean =
        BCrypt
            .verifyer()
            .verify(
                loginUserDto.password?.toCharArray(),
                user.password,
            ).verified

    private fun throwIfPasswordsNotEqual(
        password: String?,
        confirmPassword: String?,
    ) {
        if (password != confirmPassword) throw PasswordMismatchException("Passwords don't match")
    }
}
