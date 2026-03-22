package com.example.weatherkmp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherkmp.domain.usecase.GetCoordinatesUseCase
import com.example.weatherkmp.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(
    private val getCoordinatesUseCase: GetCoordinatesUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
) : ViewModel() {

    private val _weatherStateFlow = MutableStateFlow<WeatherUiState>(
        value = WeatherUiState.Initial(cityNameTextFieldValue = ""),
    )
    val weatherStateFlow = _weatherStateFlow.asStateFlow()

    fun updateCityName(cityName: String) {
        _weatherStateFlow.update { weatherUiState ->
            if (weatherUiState is WeatherUiState.Initial) {
                weatherUiState.copy(
                    cityNameTextFieldValue = cityName,
                )
            } else {
                weatherUiState
            }
        }
    }

    fun deleteUiState() {
        _weatherStateFlow.value = WeatherUiState.Initial(cityNameTextFieldValue = "")
    }

    fun loadInfo(cityName: String) {
        _weatherStateFlow.value = WeatherUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val cityDomain = getCoordinatesUseCase(cityName = cityName)
            _weatherStateFlow.value  = cityDomain?.let { cityDomain ->
                val weatherDomains = getWeatherUseCase(
                    latitude = cityDomain.latitude,
                    longitude = cityDomain.longitude,
                )
                WeatherUiState.WeatherData(
                    hourlyWeather = weatherDomains.mapIndexed { index, weatherDomain ->
                        weatherDomain.toDomain(
                            id = index,
                            time = formatDateTime(weatherDomain.time),
                        )
                    }
                )
            } ?: WeatherUiState.Error
        }
    }

    private fun formatDateTime(timeDomain: String): String {
        // "2026-03-28T15:00"
        val dateTimeParts = timeDomain.split("T")
        if (dateTimeParts.size != 2) return timeDomain

        val datePart = dateTimeParts[0] // 2026-03-28
        val timePart = dateTimeParts[1] // 15:00

        val dateParts = datePart.split("-")
        if (dateParts.size != 3) return timeDomain

        val day = dateParts[2].toIntOrNull() ?: return timeDomain
        val month = dateParts[1].toIntOrNull() ?: return timeDomain

        val monthName = when (month) {
            1 -> "января"
            2 -> "февраля"
            3 -> "марта"
            4 -> "апреля"
            5 -> "мая"
            6 -> "июня"
            7 -> "июля"
            8 -> "августа"
            9 -> "сентября"
            10 -> "октября"
            11 -> "ноября"
            12 -> "декабря"
            else -> return timeDomain
        }

        return "$day $monthName в $timePart"
    }
}

sealed interface WeatherUiState {

    data class Initial(
        val cityNameTextFieldValue: String,
    ): WeatherUiState

    data object Error: WeatherUiState

    data object Loading: WeatherUiState

    data class WeatherData(
        val hourlyWeather: List<HourlyWeatherUiData>
    ): WeatherUiState
}