package com.konradszewczuk.weatherapp.ui

import android.arch.lifecycle.ViewModel
import com.konradszewczuk.weatherapp.di.WeatherApplication
import com.konradszewczuk.weatherapp.data.remote.RemoteWeatherDataSource
import com.konradszewczuk.weatherapp.data.remote.weatherModel.WeatherResponse
import com.konradszewczuk.weatherapp.data.repository.WeatherRepository
import com.konradszewczuk.weatherapp.data.room.CityEntity
import com.konradszewczuk.weatherapp.data.room.RoomDataSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject


class WeatherViewModel : ViewModel() {
    @Inject lateinit var weatherRepository: WeatherRepository

    init {
        initializeDagger()
    }

    fun getWeather(latitude: Double, longitude: Double) = weatherRepository.getWeather(latitude, longitude).subscribeOn(Schedulers.computation())

    fun getCities() = weatherRepository.getCities()

    fun addCity(cityName: String) = weatherRepository.addCity(cityName)

    private fun initializeDagger() = WeatherApplication.appComponent.inject(this)
}