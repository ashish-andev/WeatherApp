package com.konradszewczuk.weatherapp.di

import com.konradszewczuk.weatherapp.data.repository.WeatherRepository
import com.konradszewczuk.weatherapp.data.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Binds




@Module
abstract class WeatherRepositoryModule {
    @Binds
    abstract fun bindsWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository
}