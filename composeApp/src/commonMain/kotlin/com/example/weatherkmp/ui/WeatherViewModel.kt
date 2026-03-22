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
            val result = cityDomain?.let { cityDomain ->
                val weatherDomains = getWeatherUseCase(
                    latitude = cityDomain.latitude,
                    longitude = cityDomain.longitude,
                )
                WeatherUiState.WeatherData(
                    hourlyWeather = weatherDomains.mapIndexed { index, weatherDomain ->
                        weatherDomain.toDomain(id = index)
                    }
                )
            } ?: WeatherUiState.Error

            withContext(Dispatchers.Main) {
                _weatherStateFlow.value = result
            }
        }
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