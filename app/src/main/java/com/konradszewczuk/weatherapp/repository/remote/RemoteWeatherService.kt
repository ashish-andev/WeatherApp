package com.konradszewczuk.weatherapp.repository.remote

import com.konradszewczuk.weatherapp.repository.remote.weatherModel.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface RemoteWeatherService {

    @GET("{latitude},{longitude}")
    fun requestWeatherForCity(
            @Path("latitude") latitude: String,
            @Path("longitude") longitude: String
    ): Observable<WeatherResponse>
}