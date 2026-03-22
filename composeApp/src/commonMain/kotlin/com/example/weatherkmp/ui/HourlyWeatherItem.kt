package com.example.weatherkmp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WeatherDataScreen(
    state: WeatherUiState.WeatherData,
    callbacks: WeatherCallbacks,
) {
    LazyColumn {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.height(4.dp))
                Button(onClick = callbacks.deleteUiState) {
                    Text(text = "Сбросить")
                }
            }
        }
        items(
            count = state.hourlyWeather.size,
            key = { index -> state.hourlyWeather[index].id }
        ) {
            if (state.hourlyWeather[it].id == 0) {
                Column { Spacer(modifier = Modifier.height(12.dp)) }
            }
            HourlyWeatherItem(
                hourlyWeather = state.hourlyWeather[it]
            )
            Column { Spacer(modifier = Modifier.height(12.dp)) }
        }
    }
}

@Composable
fun HourlyWeatherItem(
    hourlyWeather: HourlyWeatherUiData,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.Yellow,
                shape = RoundedCornerShape(16.dp),
            )
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = hourlyWeather.time)
        Spacer(modifier = Modifier.width(48.dp))
        Text(text = hourlyWeather.temperature.toString())
    }
}

@Preview
@Composable
private fun HourlyWeatherItemPreview() {
    MaterialTheme {
        HourlyWeatherItem(
            hourlyWeather = HourlyWeatherUiData(
                id = 0,
                time = "15:00",
                temperature = 12.0,
            )
        )
    }
}