package com.weathertracker.root.controller

import com.weathertracker.root.service.SessionService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthInterceptor(
    private val sessionService: SessionService,
) : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        val cookies = request.cookies?.find { it.name == "session_id" }?.value
        if (cookies == null || !sessionService.isValidSession(cookies)) {
            response.sendRedirect("/login")
            return false
        }
        return true
    }
}
