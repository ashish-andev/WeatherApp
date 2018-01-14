package com.konradszewczuk.weatherapp.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context



@Database(entities = arrayOf(CityEntity::class), version = 1)
abstract class RoomDataSource : RoomDatabase() {

    abstract fun weatherSearchCityDao(): WeatherCitiesDao

    companion object {

        @Volatile private var INSTANCE: RoomDataSource? = null

        fun getInstance(context: Context): RoomDataSource =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        RoomDataSource::class.java, RoomConfig.DATABASE_WEATHER)
                        .build()
    }
}