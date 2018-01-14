package com.konradszewczuk.weatherapp.data.remote.weatherModel

import com.google.gson.annotations.SerializedName
import com.konradszewczuk.weatherapp.data.remote.weatherModel.daily.Daily
import com.konradszewczuk.weatherapp.data.remote.weatherModel.hourly.Hourly
import com.konradszewczuk.weatherapp.data.remote.weatherModel.minutely.Minutely


data class WeatherResponse(
        val latitude: Double, //42.3601
        val longitude: Double, //-71.0589
        val timezone: String, //America/New_York
        val currently: Currently,
        val minutely: Minutely,
        val hourly: Hourly,
        val daily: Daily,
        val alerts: List<Alert>,
        val flags: Flags,
        val offset: Int //-5
)


data class Flags(
        val sources: List<String>,
        @SerializedName("isd-stations")
		val isdStations: List<String>,
		val units: String,
        val id: Long
)

data class Currently(
		val time: Int, //1515707975
		val summary: String, //Mostly Cloudy
		val icon: String, //partly-cloudy-night
		val nearestStormDistance: Int, //181
		val nearestStormBearing: Int, //315
		val precipIntensity: Double, //0
		val precipProbability: Double, //0
		val temperature: Double, //50.65
		val apparentTemperature: Double, //50.65
		val dewPoint: Double, //44.9
		val humidity: Double, //0.81
		val pressure: Double, //1026.14
		val windSpeed: Double, //6.12
		val windGust: Double, //11.09
		val windBearing: Int, //211
		val cloudCover: Double, //0.63
		val uvIndex: Int, //0
		val visibility: Double, //7.52
		val ozone: Double //273.79
)



data class Alert(
		val title: String, //Flood Watch
		val regions: List<String>,
		val severity: String, //watch
		val time: Int, //1515702600
		val expires: Int, //1515866400
		val description: String, //...FLOOD WATCH REMAINS IN EFFECT FROM FRIDAY MORNING THROUGH SATURDAY AFTERNOON... The Flood Watch continues for * All of southern New England * From Friday morning through Saturday afternoon * Heavy rain is expected to overspread the region from west to east Friday afternoon and evening continuing at times into Saturday morning. This combined with rapid snow melt will result in the possibility of some river and stream flooding along with the potential for isolated ice jam flooding. * The heavy rainfall combined with snow clogged storm drains may also result in the potential for significant urban/street flooding. This has the potential to impact the Friday evening commute. * Storm total rainfall amounts of 1.5 to 3 inches with locally up to 4 inches are forecast over the entire region.

		val uri: String //https://alerts.weather.gov/cap/wwacapget.php?x=MA125A8C5C3A5C.FloodWatch.125A8C7A4560MA.BOXFFABOX.9ba0631e1776c32aca9cb394b265262f
)