package com.example.weatherkmp.data.models

import com.example.weatherkmp.domain.models.HourlyWeatherDomain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("hourly")
    val hourlyWeather: HourlyWeather,
)

@Serializable
data class HourlyWeather(
    val time: List<String>,
    @SerialName("temperature_2m")
    val temperature: List<Double>,
)

fun WeatherResponse.toDomain() : List<HourlyWeatherDomain> {
    val times = hourlyWeather.time
    val temperatures = hourlyWeather.temperature

    return times.zip(temperatures) { time, temp ->
        HourlyWeatherDomain(
            time = time,
            temperature = temp,
        )
    }
}