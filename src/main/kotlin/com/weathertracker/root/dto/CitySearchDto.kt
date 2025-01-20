package com.weathertracker.root.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CitySearchDto(
    @get:NotBlank(message = "Field can not be blank")
    @get:Size(min = 1, max = 30, message = "Field must have between 1 and 30")
    val city: String = "",
)
