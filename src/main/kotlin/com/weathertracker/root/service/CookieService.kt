package com.weathertracker.root.service

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service

@Service
class CookieService {
    fun setSessionForCookie(
        response: HttpServletResponse?,
        sessionId: String,
        maxAge: Int,
    ) {
        val cookie = Cookie("session_id", sessionId)
        cookie.maxAge = maxAge
        cookie.path = "/"
        response?.addCookie(cookie)
    }

    fun removeCookie(response: HttpServletResponse?) {
        val cookie = Cookie("session_id", null)
        cookie.maxAge = 0
        cookie.path = "/"
        response?.addCookie(cookie)
    }
}
