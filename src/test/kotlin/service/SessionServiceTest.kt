package service

import com.weathertracker.root.config.spring.SpringConfiguration
import com.weathertracker.root.dto.LoginUserDto
import com.weathertracker.root.model.Session
import com.weathertracker.root.service.CookieService
import com.weathertracker.root.service.SessionService
import com.weathertracker.root.service.UserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.springframework.test.context.web.WebAppConfiguration
import java.time.LocalDateTime

@ActiveProfiles("test")
@SpringJUnitConfig(SpringConfiguration::class)
@WebAppConfiguration
class SessionServiceTest {
    @Autowired
    private lateinit var cookieService: CookieService

    @Autowired
    private lateinit var sessionService: SessionService

    @Autowired
    private lateinit var userService: UserService

    @Test
    fun deleteExpiredSessionsTest() {
        val user =
            userService.saveUser(
                LoginUserDto(
                    username = "vlad",
                    password = "vladRobloxPro",
                ),
            )
        val response = MockHttpServletResponse()
        val session = sessionService.save(Session(user = user, expiresAt = LocalDateTime.now().minusDays(1))).id
        cookieService.apply {
            removeCookie(response)
            setSessionForCookie(
                response = response,
                sessionId = session,
                maxAge = 1,
            )
        }
        Thread.sleep(1500)
        sessionService.deleteExpiredSessions()
        val sessions = sessionService.getAll()
        assertEquals(sessions.size, 0)
    }
}
