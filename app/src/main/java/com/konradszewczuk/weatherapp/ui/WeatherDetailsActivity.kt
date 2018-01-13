package com.konradszewczuk.weatherapp.ui

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.konradszewczuk.weatherapp.R
import com.konradszewczuk.weatherapp.ui.adapters.WeeklyWeatherAdapter
import com.konradszewczuk.weatherapp.ui.dto.WeatherDetailsDTO
import com.konradszewczuk.weatherapp.ui.dto.WeeklyWeatherDTO
import com.konradszewczuk.weatherapp.utils.AxisValueFormatter
import com.konradszewczuk.weatherapp.utils.WeatherMathUtils.convertToCelsius
import kotlinx.android.synthetic.main.activity_weather_details.*
import org.parceler.Parcels
import com.konradszewczuk.weatherapp.utils.ValueFormatter
import java.util.*


class WeatherDetailsActivity : AppCompatActivity() {

    private var weeklyWeatherList = ArrayList<WeeklyWeatherDTO>()
    private var adapter: WeeklyWeatherAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)
        val weatherDetails = Parcels.unwrap<WeatherDetailsDTO>(intent.getParcelableExtra("tempBundle"))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = weatherDetails.cityName

        textViewCurrentTemperature.text = "%.2f".format(weatherDetails.temperature) + " \u2103" //TODO
        textViewWeatherSummary.text = weatherDetails.weatherSummary
        textViewHumidityValue.text = weatherDetails.humidity.toString() + "%"
        textViewWindSpeedValue.text = weatherDetails.windSpeed.toString() + "m/s"
        textViewCloudCoverageValue.text = "%.2f".format(weatherDetails.cloudsPercentage) + "%"

        weatherDetails.temperature?.let { processTemperatureText(it) }

        weeklyWeatherList = weatherDetails.weeklyDayWeahterList as ArrayList<WeeklyWeatherDTO>
        adapter = WeeklyWeatherAdapter(weeklyWeatherList)

        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewWeeklyWeather.setLayoutManager(mLayoutManager)
        recyclerViewWeeklyWeather.setItemAnimator(DefaultItemAnimator())
        recyclerViewWeeklyWeather.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerViewWeeklyWeather.setAdapter(adapter)

        setDayChart(chartHourlyWeather, weatherDetails)
    }

    private fun processTemperatureText(temperature: Double) {
        when {
            temperature < 10 -> textViewCurrentTemperature.setTextColor(Color.BLUE)
            temperature in 10..20 -> textViewCurrentTemperature.setTextColor(Color.BLACK)
            temperature > 20 -> textViewCurrentTemperature.setTextColor(Color.RED)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setDayChart(lineChart: LineChart, weatherDetailsDTO: WeatherDetailsDTO) {

        val entries = ArrayList<Entry>()
        val temperatureList = ArrayList<Int>()

        //temp - add data to calculate min temperature for chart axis
        //entries - add data to display temp for every hour
        //hours - add data to display time in chart
        for (i in 0..24) {
            convertToCelsius(weatherDetailsDTO.hourlyWeatherList?.get(i)?.temperature)?.let {
                temperatureList.add(it.toInt())
                entries.add(Entry(i.toFloat(), it.toFloat()))
            }
        }

        val lineDataSet = LineDataSet(entries, "Label")
        customizeLineDataSet(lineDataSet)

        val leftAxis = lineChart.axisLeft
        setYAxis(leftAxis, temperatureList)

        val rightAxis = lineChart.axisRight
        setYAxis(rightAxis, temperatureList)

        val downAxis = lineChart.xAxis
        weatherDetailsDTO.hourlyWeatherStringFormatedHoursList?.let {
            setXAxis(downAxis, weatherDetailsDTO.hourlyWeatherStringFormatedHoursList)
        }

        val lineData = LineData(lineDataSet)
        lineDataSet.valueFormatter = ValueFormatter()
        customizeLineChart(lineChart, lineData)
    }

    private fun setXAxis(xAxis: XAxis, values: ArrayList<String>) {
        xAxis.labelCount = 25
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = AxisValueFormatter(values)
    }

    private fun customizeLineDataSet(lineDataSet: LineDataSet) {
        lineDataSet.valueTextSize = 12f
        lineDataSet.circleHoleRadius = 2.5f
        lineDataSet.circleRadius = 4f
        lineDataSet.valueFormatter = ValueFormatter()
        lineDataSet.color = R.color.colorAccent
        lineDataSet.valueTextColor = R.color.colorPrimary
    }

    private fun customizeLineChart(lineChart: LineChart, lineData: LineData) {
        val description = Description()
        description.text = ""
        lineChart.data = lineData
        lineChart.legend.isEnabled = false
        lineChart.setTouchEnabled(false)
        lineChart.description = description
        lineChart.canScrollHorizontally(1)
        lineChart.invalidate()
        lineChart.notifyDataSetChanged()
    }

    private fun setYAxis(axis: YAxis, temp: ArrayList<Int>) {
        axis.setDrawGridLines(false)
        axis.setDrawLabels(false)
        axis.axisMinimum = (Collections.min(temp) - 2).toFloat()
        axis.axisMaximum = (Collections.max(temp) + 2).toFloat()
    }

}
