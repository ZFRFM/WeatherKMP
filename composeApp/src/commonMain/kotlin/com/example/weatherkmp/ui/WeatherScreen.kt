package com.example.weatherkmp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
fun WeatherScreen(
    weatherViewModel: WeatherViewModel = koinInject(),
) {
    MaterialTheme {
        val weatherUiState by weatherViewModel.weatherStateFlow.collectAsState()
        val callbacks = WeatherCallbacks(
            onCityNameTextFieldValueChanged = weatherViewModel::updateCityName,
            deleteUiState = weatherViewModel::deleteUiState,
            loadInfo = weatherViewModel::loadInfo
        )
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when(val state = weatherUiState) {

                is WeatherUiState.Initial -> WeatherInitialScreen(
                    weatherUiState = state,
                    callbacks = callbacks,
                )

                WeatherUiState.Error -> Text("Ошибка")

                WeatherUiState.Loading -> Text("Загрузка")

                is WeatherUiState.WeatherData -> WeatherDataScreen(
                    state = state,
                    callbacks = callbacks,
                )
            }
        }
    }
}

data class WeatherCallbacks(
    val onCityNameTextFieldValueChanged: (String) -> Unit,
    val deleteUiState: () -> Unit,
    val loadInfo: (String) -> Unit,
)

@Preview
@Composable
private fun WeatherScreenPreview() {
    WeatherScreen()
}