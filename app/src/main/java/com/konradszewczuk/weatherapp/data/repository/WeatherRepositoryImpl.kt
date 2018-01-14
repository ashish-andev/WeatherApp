package com.konradszewczuk.weatherapp.data.repository

import com.konradszewczuk.weatherapp.data.remote.RemoteWeatherDataSource
import com.konradszewczuk.weatherapp.data.remote.locationModel.LocationResponse
import com.konradszewczuk.weatherapp.data.remote.weatherModel.WeatherResponse
import com.konradszewczuk.weatherapp.data.room.CityEntity
import com.konradszewczuk.weatherapp.data.room.RoomDataSource
import com.konradszewczuk.weatherapp.domain.dto.WeatherDetailsDTO
import com.konradszewczuk.weatherapp.utils.TransformersDTO
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
        private val remoteWeatherDataSource: RemoteWeatherDataSource,
        private val roomDataSource: RoomDataSource
) : WeatherRepository {

    override fun getWeather(cityName: String): Single<WeatherDetailsDTO> {
//        return remoteWeatherDataSource.requestCityAddressByName(cityName)
//                .flatMap({ responseFromServiceA: LocationResponse -> remoteWeatherDataSource.requestWeatherForCity(responseFromServiceA.results[0].geometry.location.lat.toString(), responseFromServiceA.results[0].geometry.location.lng.toString()) },
//                        { responseFromServiceA: LocationResponse, responseFromServiceB: WeatherResponse ->
//                            TransformersDTO.transformToWeatherDetailsDTO(responseFromServiceA.results[0].formatted_address, responseFromServiceB)
//                        })
//                .retry()

        return remoteWeatherDataSource.requestCityAddressByName(cityName)
                .flatMap({ a: LocationResponse ->
                    remoteWeatherDataSource.requestWeatherForCity(
                            a.results[0].geometry.location.lat.toString(),
                            a.results[0].geometry.location.lng.toString()
                    )
                            .map { b: WeatherResponse ->
                                TransformersDTO.transformToWeatherDetailsDTO(
                                        a.results[0].formatted_address,
                                        b
                                )
                            }
                })
                .retry()

    }


    override fun getCities(): Flowable<List<CityEntity>> {
        return roomDataSource.weatherSearchCityDao().getAllCities()
    }

    override fun addCity(cityName: String) {
        Completable.fromCallable { roomDataSource.weatherSearchCityDao().insertCity(CityEntity(cityName = cityName)) }.subscribeOn(Schedulers.io()).subscribe()
    }


}