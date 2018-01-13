package com.konradszewczuk.weatherapp.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.konradszewczuk.weatherapp.R
import com.konradszewczuk.weatherapp.ui.dto.WeeklyWeatherDTO
import com.konradszewczuk.weatherapp.utils.WeatherMathUtils.convertFahrenheitToCelsius
import java.util.ArrayList


class WeeklyWeatherAdapter(val list: ArrayList<WeeklyWeatherDTO>) : RecyclerView.Adapter<WeeklyWeatherAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.getContext())
                .inflate(R.layout.item_weather_day, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val item = list.get(position)
        holder?.textViewDayMaxTemp?.setText("%.2f".format(convertFahrenheitToCelsius(item.maxTemp.toDouble())))
        holder?.textViewDayMinTemp?.setText("%.2f".format(convertFahrenheitToCelsius(item.minTemp.toDouble())))
        holder?.textViewDayWeatherWeekDay?.setText(item.dayOfWeek)
        holder?.textViewDayWeatherType?.setText(item.weatherType)
    }

    class ViewHolder(view: View)  : RecyclerView.ViewHolder(view) {
        var textViewDayMaxTemp : TextView
        var textViewDayMinTemp: TextView
        var textViewDayWeatherWeekDay: TextView
        var textViewDayWeatherType: TextView


        init {
            textViewDayMaxTemp = view.findViewById(R.id.textViewDayMaxTemp)
            textViewDayMinTemp = view.findViewById(R.id.textViewDayMinTemp)
            textViewDayWeatherWeekDay = view.findViewById(R.id.textViewDayWeatherWeekDay)
            textViewDayWeatherType = view.findViewById(R.id.textViewDayWeatherType)
        }
    }
}