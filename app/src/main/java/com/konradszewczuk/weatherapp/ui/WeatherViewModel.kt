package com.konradszewczuk.weatherapp.ui

import android.arch.lifecycle.ViewModel
import com.konradszewczuk.weatherapp.di.WeatherApplication
import com.konradszewczuk.weatherapp.data.repository.WeatherRepository
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject


class WeatherViewModel : ViewModel() {
    @Inject lateinit var weatherRepository: WeatherRepository

    init {
        initializeDagger()
    }

    fun getWeather(cityName: String) = weatherRepository.getWeather(cityName)?.subscribeOn(Schedulers.computation())

    fun getCities() = weatherRepository.getCities()

    fun addCity(cityName: String) = weatherRepository.addCity(cityName)

    private fun initializeDagger() = WeatherApplication.appComponent.inject(this)
}