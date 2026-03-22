package com.example.weatherkmp.data.repository

import com.example.weatherkmp.data.models.CitiesResponse
import com.example.weatherkmp.data.models.WeatherResponse
import com.example.weatherkmp.data.models.toDomain
import com.example.weatherkmp.data.network.KtorClient
import com.example.weatherkmp.domain.models.CityDomain
import com.example.weatherkmp.domain.models.HourlyWeatherDomain
import com.example.weatherkmp.domain.repository.WeatherRepository
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL_SEARCH = "https://geocoding-api.open-meteo.com/v1/search"
private const val BASE_URL_FORECAST = "https://api.open-meteo.com/v1/forecast"

class WeatherRepositoryImpl: WeatherRepository {

    override suspend fun getCoordinates(cityName: String): CityDomain? {
        val response = KtorClient.client.get(urlString = BASE_URL_SEARCH) {
            parameter(key = "name", value = cityName)
            parameter(key = "count", value = 1)
        }.body<CitiesResponse>()
        return response.results?.firstOrNull()?.toDomain()
    }

    override suspend fun getWeather(
        latitude: Double,
        longitude: Double
    ): List<HourlyWeatherDomain> = KtorClient.client.get(urlString = BASE_URL_FORECAST) {
        parameter(key = "latitude", value = latitude)
        parameter(key = "longitude", value = longitude)
        parameter(key = "hourly", value = "temperature_2m")
    }.body<WeatherResponse>().toDomain()
}