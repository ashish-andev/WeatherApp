package com.konradszewczuk.weatherapp.repository

import android.arch.lifecycle.ViewModel
import com.konradszewczuk.weatherapp.di.WeatherApplication
import com.konradszewczuk.weatherapp.repository.remote.RemoteWeatherDataSource
import com.konradszewczuk.weatherapp.repository.remote.weatherModel.WeatherResponse
import com.konradszewczuk.weatherapp.repository.room.CityEntity
import com.konradszewczuk.weatherapp.repository.room.RoomDataSource
import com.konradszewczuk.weatherapp.ui.dto.WeatherDetailsDTO
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class WeatherViewModel: ViewModel() {

    @Inject lateinit var remoteWeatherDataSource: RemoteWeatherDataSource
    @Inject lateinit var roomDataSource: RoomDataSource

    init {
        initializeDagger()
    }

    fun getWeather(latitude: Double, longitude: Double) = remoteWeatherDataSource.requestWeatherForCity(latitude.toString(), longitude.toString()).subscribeOn(Schedulers.computation())



    fun getCities() = roomDataSource.weatherSearchCityDao().getAllCities()

    fun addCity(cityName: String) {
       Completable.fromCallable { roomDataSource.weatherSearchCityDao().insertCity(CityEntity(cityName = cityName)) }.subscribeOn(Schedulers.io()).subscribe()
    }

    private fun initializeDagger() = WeatherApplication.appComponent.inject(this)

}