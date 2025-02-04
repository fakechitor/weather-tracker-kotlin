package com.weathertracker.root.service

import at.favre.lib.crypto.bcrypt.BCrypt
import com.weathertracker.root.dto.LoginUserDto
import com.weathertracker.root.dto.SessionInfoDto
import com.weathertracker.root.dto.SignupUserDto
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
    companion object {
        private val verifier: BCrypt.Verifyer = BCrypt.verifyer()
        private val hasher: BCrypt.Hasher = BCrypt.withDefaults()
    }

    fun register(
        sessionInfoDto: SessionInfoDto,
        signupUserDto: SignupUserDto,
    ) {
        signupUserDto.throwIfPasswordsNotEqual()
        sessionService.createSessionAndAddCookie(
            sessionInfoDto = sessionInfoDto,
            user =
                userService.saveUser(
                    LoginUserDto(username = signupUserDto.username, password = signupUserDto.password?.encryptPassword()),
                ),
        )
    }

    private fun SignupUserDto.throwIfPasswordsNotEqual() {
        if (this.password != this.confirmPassword) throw PasswordMismatchException("Passwords don't match")
    }

    private fun String.encryptPassword(): String = hasher.hashToString(10, this.toCharArray())

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
            if (loginUserDto.password.isPasswordValid(user)) {
                sessionService.createSessionAndAddCookie(sessionInfoDto, user)
            } else {
                throw UserNotFoundException("User not found")
            }
        } ?: throw UserNotFoundException("User not found")
    }

    private fun String?.isPasswordValid(user: User): Boolean = verifier.verify(this?.toCharArray(), user.password).verified

    fun getUserIfAuthorized(sessionId: String): User? = userService.findById(sessionService.findById(sessionId)?.user?.id)
}
