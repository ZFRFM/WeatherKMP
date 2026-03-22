package com.example.weatherkmp.domain.usecase

import com.example.weatherkmp.domain.repository.WeatherRepository

class GetWeatherUseCase(
    private val weatherRepository: WeatherRepository,
) {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
    ) = weatherRepository.getWeather(
        latitude = latitude,
        longitude = longitude,
    )
}