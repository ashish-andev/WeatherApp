package com.konradszewczuk.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.*
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler


object StringDateFormatter {
    fun convertTimestampToDayOfTheWeek(timestamp: Int) : String{
       val formatter = SimpleDateFormat ("EEEE")
        val dayName = formatter.format(Date(timestamp.toLong()* 1000))
        return dayName
    }

    fun convertTimestampToHourFormat(timestamp: Long, timeZone: String?) : String{
        val HOUR_MINUTE = "HH:mm"
        val formatter = SimpleDateFormat (HOUR_MINUTE)
        formatter.setTimeZone(TimeZone.getTimeZone(timeZone))

        val dayName = formatter.format(Date(timestamp * 1000))
        return dayName
    }
}

class AxisValueFormatter(val values: ArrayList<String>) : IAxisValueFormatter {

    override fun getFormattedValue(value: Float, axis: AxisBase): String {
        return values[value.toInt()]
    }
}

class ValueFormatter : IValueFormatter {
    private val DEGREE = "\u00b0"

    override fun getFormattedValue(value: Float, entry: Entry, dataSetIndex: Int, viewPortHandler: ViewPortHandler): String {
        return "%.1f".format(value.toDouble()) + DEGREE
    }
}