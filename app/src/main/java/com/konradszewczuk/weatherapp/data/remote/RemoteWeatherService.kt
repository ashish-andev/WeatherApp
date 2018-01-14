package com.konradszewczuk.weatherapp.data.remote

import com.konradszewczuk.weatherapp.data.remote.weatherModel.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface RemoteWeatherService {

    @GET("{latitude},{longitude}")
    fun requestWeatherForCity(
            @Path("latitude") latitude: String,
            @Path("longitude") longitude: String
    ): Single<WeatherResponse>
}