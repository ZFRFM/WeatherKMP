package com.example.weatherkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform