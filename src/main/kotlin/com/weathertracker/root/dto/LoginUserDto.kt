package com.weathertracker.root.dto

import jakarta.validation.constraints.Size

data class LoginUserDto(
    @get:Size(min = 3, max = 30, message = "The size should be between 3 and 30")
    val username: String? = null,
    @get:Size(min = 3, max = 30, message = "The size should be between 3 and 30")
    val password: String? = null,
)
