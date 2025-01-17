package com.weathertracker.root.dto

data class WeatherDeleteDto(
    val userId: Int,
    val latitude: Double,
    val longitude: Double,
)
