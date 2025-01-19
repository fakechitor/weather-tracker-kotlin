package com.weathertracker.root.dto.mapper

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class LocationSearchResponseDto(
    val name: String,
    val country: String,
    val state: String? = null,
    @JsonProperty("lat") val latitude: Double,
    @JsonProperty("lon") val longitude: Double,
)
