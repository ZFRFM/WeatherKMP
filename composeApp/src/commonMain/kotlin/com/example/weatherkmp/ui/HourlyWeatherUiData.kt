package com.example.weatherkmp.ui

import com.example.weatherkmp.domain.models.HourlyWeatherDomain

data class HourlyWeatherUiData(
    val id: Int,
    val time: String,
    val temperature: Double,
)

fun HourlyWeatherDomain.toDomain(id: Int) = HourlyWeatherUiData(
    id = id,
    time = time,
    temperature = temperature,
)