package com.example.weatherkmp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun WeatherInitialScreen(
    weatherUiState: WeatherUiState.Initial,
    callbacks: WeatherCallbacks,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(Modifier.height(24.dp))
        // Не юзай хардкод! Мне можно)
        Text(
            text = "Тут ты можешь ввести название города и узнать погоду",
            modifier = Modifier.padding(horizontal = 32.dp),
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(24.dp))
        TextField(
            value = weatherUiState.cityNameTextFieldValue,
            onValueChange = callbacks.onCityNameTextFieldValueChanged,
        )

        Spacer(Modifier.height(24.dp))
        // Не юзай хардкод! Мне можно)
        Button(
            onClick = {
                callbacks.loadInfo(weatherUiState.cityNameTextFieldValue)
            },
        ) { Text(text = "Узнать") }
    }
}