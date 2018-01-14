package com.konradszewczuk.weatherapp.domain.dto

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel(Parcel.Serialization.BEAN)
data class HourlyWeatherDTO @ParcelConstructor constructor(val timestamp: Long,
                                                           val temperature: Double)

@Parcel(Parcel.Serialization.BEAN)
data class WeatherDetailsDTO @ParcelConstructor constructor(val cityName: String,
                                                            val weatherSummary: String?,
                                                            val temperature: Double?,
                                                            val humidity: Double?,
                                                            val windSpeed: Double?,
                                                            val cloudsPercentage: Double?,
                                                            val weeklyDayWeahterList: ArrayList<WeeklyWeatherDTO>?,
                                                            val hourlyWeatherList: ArrayList<HourlyWeatherDTO>?,
                                                            val hourlyWeatherStringFormatedHoursList: ArrayList<String>?)

@Parcel(Parcel.Serialization.BEAN)
data class WeeklyWeatherDTO @ParcelConstructor constructor(val maxTemp: String,
                                                           val minTemp: String,
                                                           val dayOfWeek: String,
                                                           val weatherType: String)
