package com.konradszewczuk.weatherapp.utils

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import com.konradszewczuk.weatherapp.utils.StringFormatter.convertToValueWithUnit
import com.konradszewczuk.weatherapp.utils.StringFormatter.unitDegrees
import java.util.ArrayList


object ChartFormatter{
    class AxisValueFormatter(val values: ArrayList<String>) : IAxisValueFormatter {

        override fun getFormattedValue(value: Float, axis: AxisBase): String {
            return values[value.toInt()]
        }
    }

    class ValueFormatter : IValueFormatter {

        override fun getFormattedValue(value: Float, entry: Entry, dataSetIndex: Int, viewPortHandler: ViewPortHandler): String {
            return convertToValueWithUnit(1, unitDegrees, value.toDouble())
        }
    }
}