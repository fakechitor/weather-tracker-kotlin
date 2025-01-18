package com.weathertracker.root.dto

import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class SignupUserDto(
    @get:Size(min = 3, max = 30, message = "The size should be between 3 and 30")
    val username: String? = null,
    @get:Size(min = 3, max = 30, message = "The size should be between 3 and 30")
    @get:Pattern(regexp = ".*[a-zA-Z]+.*", message = "The field must contain at least one letter")
    val password: String? = null,
    @get:Size(min = 3, max = 30, message = "The size should be between 3 and 30")
    @get:Pattern(regexp = ".*[a-zA-Z]+.*", message = "The field must contain at least one letter")
    val confirmPassword: String? = null,
)
