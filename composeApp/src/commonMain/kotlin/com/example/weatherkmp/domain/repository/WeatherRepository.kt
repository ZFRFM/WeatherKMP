package com.example.weatherkmp.domain.repository

import com.example.weatherkmp.domain.models.CityDomain
import com.example.weatherkmp.domain.models.HourlyWeatherDomain

interface WeatherRepository {

    suspend fun getCoordinates(cityName: String): CityDomain?

    suspend fun getWeather(latitude: Double, longitude: Double): List<HourlyWeatherDomain>
}