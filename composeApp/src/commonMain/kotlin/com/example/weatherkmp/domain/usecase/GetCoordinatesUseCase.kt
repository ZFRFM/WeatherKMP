package com.example.weatherkmp.domain.usecase

import com.example.weatherkmp.domain.repository.WeatherRepository

class GetCoordinatesUseCase(
    private val weatherRepository: WeatherRepository,
) {

    suspend operator fun invoke(
        cityName: String,
    ) = weatherRepository.getCoordinates(
        cityName = cityName,
    )
}