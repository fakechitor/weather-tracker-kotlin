package com.weathertracker.root.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class LocationDto(
    val name: String,
    @JsonProperty("lat") val latitude: Double,
    @JsonProperty("lon") val longitude: Double
)
