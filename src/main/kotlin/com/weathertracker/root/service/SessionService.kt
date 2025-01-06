package com.weathertracker.root.service

import com.weathertracker.root.dto.SessionInfoDto
import com.weathertracker.root.model.Session
import com.weathertracker.root.model.User
import com.weathertracker.root.repository.SessionRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime

const val MAX_COOKIE_AGE = 36000

@Service
class SessionService(
    private val sessionRepository: SessionRepository,
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

    fun deleteSession(session: Session) = sessionRepository.delete(session)

    fun deleteSessionById(sessionId: String) = getSession(sessionId)?.let { sessionRepository.delete(it) }

    fun createSession(
        sessionInfoDto: SessionInfoDto,
        user: User?,
    ) {
        cookieService.removeCookie(sessionInfoDto.response)
        val session = save(Session(user = user, expiresAt = getAgeForSession()))
        cookieService.setSessionForCookie(sessionId = session.id, response = sessionInfoDto.response)
    }

    @Scheduled(fixedRate = 60000)
    fun deleteExpiredSessions() = sessionRepository.deleteExpiredSessions()

    private fun save(session: Session): Session = sessionRepository.save(session)

    private fun isSessionExpired(session: Session): Boolean? = session.expiresAt?.isBefore(LocalDateTime.now())

    private fun getAgeForSession(): LocalDateTime = LocalDateTime.now().plusSeconds(MAX_COOKIE_AGE.toLong())
}
