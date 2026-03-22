package com.example.weatherkmp

import androidx.compose.ui.window.ComposeUIViewController
import com.example.weatherkmp.di.initKoin
import com.example.weatherkmp.ui.WeatherScreen

fun MainViewController() = ComposeUIViewController {
    // надо придумать как инициализацию коина перенести выше в вызове
    initKoin()
    WeatherScreen()
}