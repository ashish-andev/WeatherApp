package com.konradszewczuk.weatherapp.data.repository

import com.konradszewczuk.weatherapp.data.remote.weatherModel.WeatherResponse
import com.konradszewczuk.weatherapp.data.room.CityEntity
import io.reactivex.Flowable
import io.reactivex.Observable


interface WeatherRepository {

    fun getWeather(latitude: Double, longitude: Double): Observable<WeatherResponse>

    fun getCities(): Flowable<List<CityEntity>>

    fun addCity(cityName: String)
}