package com.konradszewczuk.weatherapp.di

import android.content.Context
import com.konradszewczuk.weatherapp.data.repository.WeatherRepository
import com.konradszewczuk.weatherapp.ui.WeatherViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val weatherApplication: WeatherApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = weatherApplication

    @Provides
    @Singleton
    fun provideViewModelFactory(weatherRepository: WeatherRepository): WeatherViewModelFactory{
        return WeatherViewModelFactory(weatherRepository)
    }

}
