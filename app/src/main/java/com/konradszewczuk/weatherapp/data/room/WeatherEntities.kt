package com.konradszewczuk.weatherapp.data.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = RoomConfig.TABLE_CITIES)
data class CityEntity(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        var cityName: String
)