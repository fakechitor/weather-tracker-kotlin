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
    fun isValidSession(sessionId: String): Boolean = findById(sessionId) != null

    fun findById(sessionId: String): Session? {
        val session = sessionRepository.findById(sessionId)
        if (session != null && isSessionExpired(session) == true) {
            deleteSessionById(sessionId)
            return null
        }
        return session
    }

    fun getAll(): List<Session> = sessionRepository.getAll()

    fun deleteSessionById(sessionId: String) = findById(sessionId)?.let { sessionRepository.delete(it) }

    fun createSessionAndAddCookie(
        sessionInfoDto: SessionInfoDto,
        user: User?,
    ) = cookieService.apply {
        removeCookie(sessionInfoDto.response)
        setSessionForCookie(
            sessionId = save(Session(user = user, expiresAt = getAgeForSession())).id,
            response = sessionInfoDto.response,
            maxAge = MAX_COOKIE_AGE,
        )
    }

    @Scheduled(fixedRate = 60000)
    fun deleteExpiredSessions() = sessionRepository.deleteExpiredSessions()

    fun save(session: Session): Session = sessionRepository.save(session)

    private fun isSessionExpired(session: Session): Boolean? = session.expiresAt?.isBefore(LocalDateTime.now())

    fun getAgeForSession(): LocalDateTime = LocalDateTime.now().plusSeconds(MAX_COOKIE_AGE.toLong())
}
