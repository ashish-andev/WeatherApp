package com.konradszewczuk.weatherapp.data.repository

import com.konradszewczuk.weatherapp.data.room.CityEntity
import com.konradszewczuk.weatherapp.domain.dto.WeatherDetailsDTO
import io.reactivex.Flowable
import io.reactivex.Single


interface WeatherRepository {

    fun getCities(): Flowable<List<CityEntity>>

    fun getWeather(cityName: String): Single<WeatherDetailsDTO>

    fun addCity(cityName: String)
}