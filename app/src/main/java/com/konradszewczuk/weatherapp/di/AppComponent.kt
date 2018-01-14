package com.konradszewczuk.weatherapp.di

import com.konradszewczuk.weatherapp.ui.WeatherCitySearchActivity
import dagger.Component
import javax.inject.Singleton


@Component(modules = arrayOf(AppModule::class, RemoteModule::class, RoomModule::class, WeatherRepositoryModule::class))
@Singleton
interface AppComponent {

    fun inject(weatherCitySearchActivity: WeatherCitySearchActivity)
}