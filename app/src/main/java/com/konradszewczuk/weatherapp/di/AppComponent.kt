package com.konradszewczuk.weatherapp.di

import com.konradszewczuk.weatherapp.repository.WeatherViewModel
import dagger.Component
import javax.inject.Singleton


@Component(modules = arrayOf(AppModule::class, RemoteModule::class))
@Singleton
interface AppComponent {

    fun inject(weatherViewModel: WeatherViewModel)

}