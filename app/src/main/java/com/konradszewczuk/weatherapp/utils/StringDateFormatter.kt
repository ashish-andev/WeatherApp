package com.konradszewczuk.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.*


object StringDateFormatter {
    fun convertTimestampToDayOfTheWeek(timestamp: Int) : String{
       val formatter = SimpleDateFormat ("EEEE")
        val dayName = formatter.format(Date(timestamp.toLong()* 1000))
        return dayName
    }
}