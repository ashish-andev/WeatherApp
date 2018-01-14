package com.konradszewczuk.weatherapp

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.konradszewczuk.weatherapp.data.room.CityEntity
import com.konradszewczuk.weatherapp.data.room.RoomDataSource
import org.junit.After

import org.junit.runner.RunWith

import org.junit.Before
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class WeatherDaoTest {
    private lateinit var database: RoomDataSource

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                RoomDataSource::class.java)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun closeDb() {
        database.close()
    }


    @Test
    fun getNoCitiesWhenNoSearchedCityInserted() {
        database.weatherSearchCityDao().getAllCities()
                .test()
                .assertNoValues()
    }

    @Test
    fun insertAndGetSingleSearchedCity() {
        val searchedCityName = "Cracow"

        database.weatherSearchCityDao().insertCity(CityEntity(cityName = searchedCityName))

        val firstEmmission = database.weatherSearchCityDao().getAllCities()
                .take(1)
                .test()

        firstEmmission.awaitTerminalEvent()

        firstEmmission
                .assertNoErrors()
                .assertValue {
                    it.size == 1 && it[0].cityName.equals(searchedCityName)
                }
    }

}
