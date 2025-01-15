package com.weathertracker.root.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class LocationDto(
    val name: String,
    val country: String,
    @JsonProperty("lat") val latitude: Double,
    @JsonProperty("lon") val longitude: Double
)