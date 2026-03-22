package com.example.weatherkmp.di

import com.example.weatherkmp.data.network.createHttpClient
import com.example.weatherkmp.data.repository.WeatherRepositoryImpl
import com.example.weatherkmp.domain.repository.WeatherRepository
import com.example.weatherkmp.domain.usecase.GetCoordinatesUseCase
import com.example.weatherkmp.domain.usecase.GetWeatherUseCase
import com.example.weatherkmp.ui.WeatherViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    single { createHttpClient() }

    single { WeatherRepositoryImpl() } bind WeatherRepository::class

    factoryOf(::GetCoordinatesUseCase)

    factoryOf(::GetWeatherUseCase)

    single {
        WeatherViewModel(
            getCoordinatesUseCase = get(),
            getWeatherUseCase = get(),
        )
    }
}

fun initKoin() {
    startKoin {
        modules(appModule)
    }
}

object KoinInit {
    fun init() {
        startKoin {
            modules(appModule)
        }
    }
}