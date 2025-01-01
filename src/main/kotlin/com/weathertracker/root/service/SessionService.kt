package com.weathertracker.root.service

import com.weathertracker.root.dto.HttpContextDto
import com.weathertracker.root.model.Session
import com.weathertracker.root.repository.SessionRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime

const val MAX_COOKIE_AGE = 36000

@Service
class SessionService(
    private val sessionRepository: SessionRepository,
    private val userService: UserService,
    private val cookieService: CookieService,
) {
    fun isValidSession(sessionId: String): Boolean = getSession(sessionId) != null

    fun getSession(sessionId: String): Session? {
        val session = sessionRepository.findById(sessionId)
        if (session != null && isSessionExpired(session) == true) {
            deleteSessionById(sessionId)
            return null
        }
        return session
    }

    fun save(session: Session): Session = sessionRepository.save(session)

    fun deleteSession(session: Session) = sessionRepository.delete(session)

    fun deleteSessionById(sessionId: String) = getSession(sessionId)?.let { sessionRepository.delete(it) }

    fun createSessionIfNotExist(httpContextDto: HttpContextDto) {
        val sessionCookie = httpContextDto.request.cookies?.find { it.name == "session_id" }
        if (sessionCookie == null) {
            val session = save(Session(user = userService.findByLoginAndPassword(httpContextDto.userDto), expiresAt = getAgeForSession()))
            cookieService.setSessionForCookie(sessionId = session.id, response = httpContextDto.response)
        }
    }

    @Scheduled(fixedRate = 1800000)
    fun deleteExpiredSessions() = sessionRepository.deleteExpiredSessions()

    private fun isSessionExpired(session: Session): Boolean? = session.expiresAt?.isBefore(LocalDateTime.now())

    private fun getAgeForSession(): LocalDateTime = LocalDateTime.now().plusSeconds(MAX_COOKIE_AGE.toLong())
}
