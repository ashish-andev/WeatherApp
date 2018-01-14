package com.konradszewczuk.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.*


object StringFormatter {
    val unitPercentage = "%"
    val unitDegrees = "\u00b0"
    val unitsMetresPerSecond = "m/s"
    val unitDegreesCelsius = "\u2103"

    fun convertTimestampToDayOfTheWeek(timestamp: Int) : String{
       val formatter = SimpleDateFormat ("EEEE", Locale.ENGLISH)
        val dayName = formatter.format(Date(timestamp.toLong()* 1000))
        return dayName
    }

    fun convertTimestampToDayAndHourFormat(timestamp: Long): String{
        val DAY_HOUR_MINUTE = "EEEE, HH:mm"
        val formatter = SimpleDateFormat (DAY_HOUR_MINUTE, Locale.ENGLISH)

        val dateFormat = formatter.format(Date(timestamp))
        return dateFormat
    }

    fun convertTimestampToHourFormat(timestamp: Long, timeZone: String?) : String{
        val HOUR_MINUTE = "HH:mm"
        val formatter = SimpleDateFormat (HOUR_MINUTE)
        formatter.setTimeZone(TimeZone.getTimeZone(timeZone))

        val dayName = formatter.format(Date(timestamp * 1000))
        return dayName
    }

    fun convertToValueWithUnit(precision: Int, unitSymbol: String, value: Double?): String{
        return getPrecision(precision).format(value) + unitSymbol
    }

    private fun getPrecision(precision: Int) : String{
        return "%." + precision + "f"
    }
}

