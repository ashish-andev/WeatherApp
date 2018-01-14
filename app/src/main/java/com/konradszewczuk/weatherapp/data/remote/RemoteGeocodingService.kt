package com.konradszewczuk.weatherapp.data.remote

import com.konradszewczuk.weatherapp.data.remote.locationModel.LocationResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface RemoteGeocodingService {

    @GET("json")
    fun requestCityAddressByName(
            @Query("address") address: String
    ): Single<LocationResponse>
}