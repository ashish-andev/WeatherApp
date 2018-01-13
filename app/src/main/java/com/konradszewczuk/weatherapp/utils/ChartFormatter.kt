package com.konradszewczuk.weatherapp.utils

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import java.util.ArrayList

/**
 * Created by Admin on 2018-01-13.
 */
object ChartFormatter{
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
}