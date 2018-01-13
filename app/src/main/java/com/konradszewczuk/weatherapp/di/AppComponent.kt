package com.konradszewczuk.weatherapp.di

import com.konradszewczuk.weatherapp.ui.WeatherViewModel
import dagger.Component
import javax.inject.Singleton


@Component(modules = arrayOf(AppModule::class, RemoteModule::class, RoomModule::class))
@Singleton
interface AppComponent {

    fun inject(weatherViewModel: WeatherViewModel)

}