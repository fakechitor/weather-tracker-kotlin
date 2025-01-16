package com.weathertracker.root.dto

data class LocationInfoDto(
    val city: String,
    val country: String,
    val temperature: Double,
    val humidity: Int,
    val iconCode: String,
)
