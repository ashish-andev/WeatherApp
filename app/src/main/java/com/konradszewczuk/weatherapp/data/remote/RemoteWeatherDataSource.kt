package com.konradszewczuk.weatherapp.data.remote

import com.konradszewczuk.weatherapp.data.remote.locationModel.LocationResponse
import com.konradszewczuk.weatherapp.data.remote.weatherModel.WeatherResponse
import io.reactivex.Single
import javax.inject.Inject


class RemoteWeatherDataSource @Inject constructor(private val remoteWeatherService: RemoteWeatherService, private val remoteGeocodingService: RemoteGeocodingService) {

    fun requestWeatherForCity(latitude: String, longitude: String): Single<WeatherResponse> =
            remoteWeatherService.requestWeatherForCity(latitude, longitude)

    fun requestCityAddressByName(cityName: String): Single<LocationResponse> = remoteGeocodingService.requestCityAddressByName(cityName)
}