package com.konradszewczuk.weatherapp.repository.room


class RoomConfig {
    companion object {

        const val DATABASE_WEATHER = "weather.db"
        const val TABLE_CITIES= "cities"

        private const val SELECT_COUNT = "SELECT COUNT(*) FROM "
        private const val SELECT_FROM = "SELECT * FROM "

        const val SELECT_CITIES_COUNT = SELECT_COUNT + TABLE_CITIES
        const val SELECT_CITIES = SELECT_FROM + TABLE_CITIES
    }
}