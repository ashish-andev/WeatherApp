package com.konradszewczuk.weatherapp.data.remote

import javax.inject.Inject


class RemoteWeatherDataSource @Inject constructor(private val remoteWeatherService: RemoteWeatherService, private val remoteGeocodingService: RemoteGeocodingService) {

    fun requestWeatherForCity(latitude: String, longitude: String) =
            remoteWeatherService.requestWeatherForCity(latitude, longitude)

    fun requestCityAddressByName(cityName: String) = remoteGeocodingService.requestCityAddressByName(cityName)
}