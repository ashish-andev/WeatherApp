package com.konradszewczuk.weatherapp.data.repository

import com.konradszewczuk.weatherapp.data.remote.RemoteWeatherDataSource
import com.konradszewczuk.weatherapp.data.remote.weatherModel.WeatherResponse
import com.konradszewczuk.weatherapp.data.room.CityEntity
import com.konradszewczuk.weatherapp.data.room.RoomDataSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl@Inject constructor(
        private val remoteWeatherDataSource: RemoteWeatherDataSource,
        private val roomDataSource: RoomDataSource
) : WeatherRepository{


    override fun getWeather(latitude: Double, longitude: Double): Observable<WeatherResponse> {
        return remoteWeatherDataSource.requestWeatherForCity(latitude.toString(), longitude.toString())
    }

    override fun getCities(): Flowable<List<CityEntity>> {
        return roomDataSource.weatherSearchCityDao().getAllCities()
    }

    override fun addCity(cityName: String) {
        Completable.fromCallable { roomDataSource.weatherSearchCityDao().insertCity(CityEntity(cityName = cityName)) }.subscribeOn(Schedulers.io()).subscribe()
    }


}