package com.weathertracker.root.dto

import jakarta.servlet.http.HttpServletResponse

data class SessionInfoDto(
    val sessionId: String? = null,
    val response: HttpServletResponse? = null,
)
