package com.weathertracker.root.dto

import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class LoginUserDto(
    @get:Size(min = 3, max = 30, message = "The size should be between 3 and 30")
    @get:Pattern(regexp = "^[a-zA-Z0-9]+$", message = "The field must contain only numbers and english letters")
    val username: String? = null,
    @get:Size(min = 3, max = 30, message = "The size should be between 3 and 30")
    @get:Pattern(regexp = "^[a-zA-Z0-9]+$", message = "The field must contain only numbers and english letters")
    val password: String? = null,
)
