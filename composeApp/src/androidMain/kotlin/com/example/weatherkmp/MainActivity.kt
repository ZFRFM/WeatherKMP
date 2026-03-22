package com.example.weatherkmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherkmp.di.initKoin
import com.example.weatherkmp.ui.WeatherScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        initKoin()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            WeatherScreen()
        }
    }
}

@Preview
@Composable
fun WeatherScreenAndroidPreview() {
    WeatherScreen()
}