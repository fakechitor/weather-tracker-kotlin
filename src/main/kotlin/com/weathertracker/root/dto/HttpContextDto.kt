package com.weathertracker.root.dto

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

data class HttpContextDto(
    val request: HttpServletRequest,
    val response: HttpServletResponse,
    val userDto: UserDto,
)
