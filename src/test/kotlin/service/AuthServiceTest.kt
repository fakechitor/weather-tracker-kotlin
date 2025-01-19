package service

import com.weathertracker.root.config.spring.SpringConfiguration
import com.weathertracker.root.dto.LoginUserDto
import com.weathertracker.root.dto.SessionInfoDto
import com.weathertracker.root.dto.SignupUserDto
import com.weathertracker.root.exception.UserAlreadyExistsException
import com.weathertracker.root.exception.UserNotFoundException
import com.weathertracker.root.service.AuthService
import com.weathertracker.root.service.SessionService
import com.weathertracker.root.service.UserService
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.test.assertEquals

@ActiveProfiles("test")
@SpringJUnitConfig(SpringConfiguration::class)
@WebAppConfiguration
@Transactional(transactionManager = "transactionManager")
@Rollback
class AuthServiceTest {
    @Autowired
    private lateinit var sessionService: SessionService

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var authService: AuthService

    @Test
    fun userCreationAfterSignupTest() {
        authService.register(
            sessionInfoDto = SessionInfoDto(sessionId = UUID.randomUUID().toString(), response = MockHttpServletResponse()),
            signupUserDto = SignupUserDto(username = "anton", password = "anton2008", confirmPassword = "anton2008"),
        )
        val user = userService.findByLogin(LoginUserDto("anton", "anton2008"))
        assertNotNull(user)
        assertEquals("anton", user?.login)
    }

    @Test
    fun sessionCreationAfterSignupTest() {
        val response = MockHttpServletResponse()
        authService.register(
            sessionInfoDto = SessionInfoDto(sessionId = "", response = response),
            signupUserDto = SignupUserDto(username = "kolya", password = "kolya228", confirmPassword = "kolya228"),
        )

        val session = sessionService.findById(response.cookies.find { it.name == "session_id" && it.maxAge != 0 }?.value ?: "")
        assertNotNull(session)
        assertEquals("kolya", session?.user?.login)
    }

    @Test
    fun userAlreadyExistsTest() {
        authService.register(
            sessionInfoDto = SessionInfoDto(sessionId = UUID.randomUUID().toString(), response = MockHttpServletResponse()),
            signupUserDto = SignupUserDto(username = "anton", password = "anton2008", confirmPassword = "anton2008"),
        )
        val exception =
            assertThrows<UserAlreadyExistsException> {
                authService.register(
                    sessionInfoDto = SessionInfoDto(sessionId = UUID.randomUUID().toString(), response = MockHttpServletResponse()),
                    signupUserDto = SignupUserDto(username = "anton", password = "anton2012", confirmPassword = "anton2012"),
                )
            }
        assertEquals("User already exists", exception.message)
    }

    @Test
    fun userNotFoundTest() {
        val exception =
            assertThrows<UserNotFoundException> {
                authService.tryAuthenticateAndCreateSession(
                    loginUserDto = LoginUserDto("anton", "anton2008"),
                    sessionInfoDto = SessionInfoDto(sessionId = UUID.randomUUID().toString(), response = MockHttpServletResponse()),
                )
            }
        assertEquals("User not found", exception.message)
    }
}
