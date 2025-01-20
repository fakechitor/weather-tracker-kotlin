package com.weathertracker.root.dto

data class LocationInfoDto(
    val id: Int?,
    val latitude: Double,
    val longitude: Double,
    val city: String,
    val country: String,
    val temperature: Double,
    val temperatureFeels: Double,
    val humidity: Int,
    val iconCode: String,
)
