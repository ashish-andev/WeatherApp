package com.konradszewczuk.weatherapp.data.room

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface WeatherCitiesDao {

    @Insert
    fun insertAll(cities: List<CityEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: CityEntity)

    @Query(RoomConfig.SELECT_CITIES)
    fun getAllCities(): Flowable<List<CityEntity>>
}