package com.konradszewczuk.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.*
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.text.DateFormat


object StringDateFormatter {
    fun convertTimestampToDayOfTheWeek(timestamp: Int) : String{
       val formatter = SimpleDateFormat ("EEEE")
        val dayName = formatter.format(Date(timestamp.toLong()* 1000))
        return dayName
    }

    fun convertTimestampToHourFormat(timestamp: Long) : String{
        val formatter = SimpleDateFormat ("HH:hh")
        val dayName = formatter.format(Date(timestamp * 1000))
        return dayName
    }
}

class HourAxisValueFormatter(private val referenceTimestamp: Long // minimum timestamp in your data set
) : IAxisValueFormatter {
    private val mDataFormat: DateFormat
    private val mDate: Date

    val decimalDigits: Int
        get() = 0

    init {
        this.mDataFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        this.mDate = Date()
    }


    /**
     * Called when a value from an axis is to be formatted
     * before being drawn. For performance reasons, avoid excessive calculations
     * and memory allocations inside this method.
     *
     * @param value the value to be formatted
     * @param axis  the axis the value belongs to
     * @return
     */
    override fun getFormattedValue(value: Float, axis: AxisBase): String {
        // convertedTimestamp = originalTimestamp - referenceTimestamp
        val convertedTimestamp = value.toLong()

        // Retrieve original timestamp
        val originalTimestamp = referenceTimestamp + convertedTimestamp

        // Convert timestamp to hour:minute
        return getHour(originalTimestamp)
    }

    private fun getHour(timestamp: Long): String {
        try {
            mDate.time = timestamp * 1000
            return mDataFormat.format(mDate)
        } catch (ex: Exception) {
            return "xx"
        }

    }
}