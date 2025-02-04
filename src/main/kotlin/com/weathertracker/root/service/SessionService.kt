package com.weathertracker.root.service

import com.weathertracker.root.dto.SessionInfoDto
import com.weathertracker.root.model.Session
import com.weathertracker.root.model.User
import com.weathertracker.root.repository.SessionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

const val MAX_COOKIE_AGE = 36000

@Service
class SessionService(
    private val sessionRepository: SessionRepository,
    private val cookieService: CookieService,
) {
    @Autowired
    private lateinit var self: SessionService

    fun isValidSession(sessionId: String): Boolean = self.findById(sessionId) != null

    @Transactional
    fun findById(sessionId: String): Session? {
        val session = sessionRepository.findById(sessionId)
        if (session != null && isSessionExpired(session) == true) {
            self.delete(session)
            return null
        }
        return session
    }

    @Transactional(readOnly = true)
    fun getAll(): List<Session> = sessionRepository.getAll()

    @Transactional
    fun deleteSessionById(sessionId: String) = findById(sessionId)?.let { sessionRepository.delete(it) }

    @Transactional
    fun delete(session: Session) = sessionRepository.delete(session)

    @Transactional
    fun createSessionAndAddCookie(
        sessionInfoDto: SessionInfoDto,
        user: User?,
    ) = cookieService.apply {
        removeCookie(sessionInfoDto.response)
        setSessionForCookie(
            sessionId = self.save(Session(user = user, expiresAt = getAgeForSession())).id,
            response = sessionInfoDto.response,
            maxAge = MAX_COOKIE_AGE,
        )
    }

    @Scheduled(fixedRate = 60000)
    fun scheduleDeleteExpiredSessions() = self.deleteExpiredSessions()

    @Transactional
    fun deleteExpiredSessions() = sessionRepository.deleteExpiredSessions()

    @Transactional
    fun save(session: Session): Session = sessionRepository.save(session)

    private fun isSessionExpired(session: Session): Boolean? = session.expiresAt?.isBefore(LocalDateTime.now())

    private fun getAgeForSession(): LocalDateTime = LocalDateTime.now().plusSeconds(MAX_COOKIE_AGE.toLong())
}
