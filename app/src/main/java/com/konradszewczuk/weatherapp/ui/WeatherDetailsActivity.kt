package com.konradszewczuk.weatherapp.ui

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.format.DateUtils
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.konradszewczuk.weatherapp.R
import com.konradszewczuk.weatherapp.ui.adapters.WeeklyWeatherAdapter
import com.konradszewczuk.weatherapp.ui.dto.WeatherDetailsDTO
import com.konradszewczuk.weatherapp.ui.dto.WeeklyWeatherDTO
import com.konradszewczuk.weatherapp.utils.StringDateFormatter.convertTimestampToHourFormat
import com.konradszewczuk.weatherapp.utils.WeatherMathUtils.convertToCelsius
import kotlinx.android.synthetic.main.activity_weather_details.*
import org.parceler.Parcels
import java.util.ArrayList
import com.konradszewczuk.weatherapp.utils.HourAxisValueFormatter




class WeatherDetailsActivity : AppCompatActivity() {

    private var weeklyWeatherList = ArrayList<WeeklyWeatherDTO>()
    private var adapter: WeeklyWeatherAdapter? = null
    var currentMinReferenceTimestamp: Long? = null

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

        val entries = ArrayList<Entry>()
        currentMinReferenceTimestamp = weatherDetails.hourlyWeatherList?.get(0)?.timestamp

        weatherDetails.hourlyWeatherList?.forEach {
            if(DateUtils.isToday(it.timestamp * 1000)){
                val currentTimestamp = it.timestamp
                currentMinReferenceTimestamp?.let {
                    if(it > currentTimestamp){
                        currentMinReferenceTimestamp = currentTimestamp
                    }
                }
                //TODO populate specific view for x-Axis hour display
                convertTimestampToHourFormat(it.timestamp)
                val timestamp = it.timestamp

                convertToCelsius(it.temperature)?.let {
                    entries.add(Entry(timestamp.toFloat(), it.toFloat()))
                }
            }

        }

        val lineDataSet  = LineDataSet(entries, "Label") // add entries to dataset
        lineDataSet.setColor(Color.BLACK);
        lineDataSet.valueTextSize = 16f

        val lineData = LineData(lineDataSet)
        chartHourlyWeather.setData(lineData)
        chartHourlyWeather.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM)
        chartHourlyWeather.getXAxis().setLabelCount(25)
        currentMinReferenceTimestamp?.let {
            val xAxisFormatter = HourAxisValueFormatter(it)
            val xAxis = chartHourlyWeather.getXAxis()
            xAxis.setValueFormatter(xAxisFormatter)
        }

        chartHourlyWeather.axisRight.setDrawGridLines(false)
        chartHourlyWeather.axisRight.isEnabled = false
        chartHourlyWeather.axisLeft.isEnabled = false
        chartHourlyWeather.getAxisLeft().setDrawGridLines(false)
        chartHourlyWeather.getXAxis().setDrawGridLines(false)
        chartHourlyWeather.canScrollHorizontally(1)
        chartHourlyWeather.setVisibleYRangeMaximum(6f, YAxis.AxisDependency.RIGHT)
//        chartHourlyWeather.setVisibleXRangeMaximum(100f) // allow 20 values to be displayed at once on the x-axis, not more
//        chartHourlyWeather.moveViewToX(10f)
        chartHourlyWeather.legend.isEnabled = false
        chartHourlyWeather.description.isEnabled = false
        chartHourlyWeather.invalidate() // refresh
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
}
