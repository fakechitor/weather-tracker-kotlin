package com.weathertracker.root.interceptor

import com.weathertracker.root.service.CookieService
import com.weathertracker.root.service.SessionService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.util.WebUtils

@Component
class UnauthenticatedRedirectInterceptor(
    private val sessionService: SessionService,
    private val cookieService: CookieService,
) : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        val sessionId = WebUtils.getCookie(request, "session_id")?.value
        if (sessionId == null || !sessionService.isValidSession(sessionId)) {
            cookieService.removeCookie(response)
            response.sendRedirect("/login")
            return false
        }
        return true
    }
}
