package com.example.weatherkmp.data.models

import com.example.weatherkmp.domain.models.CityDomain
import kotlinx.serialization.Serializable

@Serializable
data class CitiesResponse(
    val results: List<CityResponse>? = null,
)

@Serializable
data class CityResponse(
    val name: String,
    val latitude: Double,
    val longitude: Double,
)

fun CityResponse.toDomain() = CityDomain(
    name = name,
    latitude = latitude,
    longitude = longitude,
)