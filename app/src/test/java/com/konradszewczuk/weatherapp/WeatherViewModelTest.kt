package com.konradszewczuk.weatherapp

import com.konradszewczuk.weatherapp.data.repository.WeatherRepository
import com.konradszewczuk.weatherapp.data.room.CityEntity
import com.konradszewczuk.weatherapp.domain.dto.HourlyWeatherDTO
import com.konradszewczuk.weatherapp.domain.dto.WeatherDetailsDTO
import com.konradszewczuk.weatherapp.domain.dto.WeeklyWeatherDTO
import com.konradszewczuk.weatherapp.ui.WeatherViewModel
import io.reactivex.Flowable
import io.reactivex.Single
import junit.framework.Assert.assertEquals

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {

    @Mock
    private lateinit var weatherRepository: WeatherRepository

    @Captor
    private lateinit var stringCityNameArgumentCaptor: ArgumentCaptor<String>

    private lateinit var weatherViewModel: WeatherViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        weatherViewModel = WeatherViewModel(weatherRepository)
        weeklyWeatherDtoArray.add(weeklyWeatherDTO)
        hourlyWeatherDtoArray.add(hourlyWeatherDTO)
    }

    @Test
    fun testGetWeather_fetchWeatherFromRepository(){
        val searchedCityName = "Cracow"
        val weatherDetailsDTO = WeatherDetailsDTO(searchedCityName, "Summary", 23.23, 23.23, 12.42, 15.2, weeklyWeatherDtoArray, hourlyWeatherDtoArray, hourlyFormatedStrings)

        Mockito.`when`(weatherRepository.getWeather(searchedCityName)).thenReturn(Single.just(weatherDetailsDTO))

        weatherViewModel.getWeather(searchedCityName)

        Mockito.verify<WeatherRepository>(weatherRepository).getWeather(capture(stringCityNameArgumentCaptor))

        assertEquals(searchedCityName, stringCityNameArgumentCaptor.value)
    }

    @Test
    fun testGetCities_fetchCitiesFromRepository(){
        val searchedCityName = "Cracow"

        Mockito.`when`(weatherRepository.getCities()).thenReturn(Flowable.just(listOf(CityEntity(cityName = searchedCityName))))

        weatherViewModel.getCities()
                .firstElement()
                .test()
                .assertNoErrors()
                .assertValue{
                    list: List<CityEntity> -> list.size == 1 && list[0].cityName.equals(searchedCityName)
                }

        Mockito.verify<WeatherRepository>(weatherRepository).getCities()
    }

    @Test
    fun testAddCity_addsCityToRepository(){
        val searchedCityName = "Cracow"

        weatherViewModel.addCity(searchedCityName)

        Mockito.verify<WeatherRepository>(weatherRepository).addCity(capture(stringCityNameArgumentCaptor))

        assertEquals(searchedCityName, stringCityNameArgumentCaptor.value)
    }


    companion object {
        val weeklyWeatherDTO = WeeklyWeatherDTO("34", "12", "Monday", "Rainy")
        val weeklyWeatherDtoArray = ArrayList<WeeklyWeatherDTO>()

        val hourlyWeatherDTO = HourlyWeatherDTO(123123L, 2.2)
        val hourlyWeatherDtoArray = ArrayList<HourlyWeatherDTO>()

        val hourlyFormatedStrings = ArrayList<String>()

    }
}