package com.konradszewczuk.weatherapp.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.konradszewczuk.weatherapp.R

import android.widget.ArrayAdapter
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.RxTextView
import com.konradszewczuk.weatherapp.data.remote.weatherModel.WeatherResponse
import com.konradszewczuk.weatherapp.data.room.CityEntity

import com.konradszewczuk.weatherapp.utils.InputValidator.isValidCityInput
import com.konradszewczuk.weatherapp.utils.TransformersDTO.transformToWeatherDetailsDTO
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_weather_city_search.*
import org.parceler.Parcels
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.disposables.Disposable


class WeatherCitySearchActivity : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var address: Address
    private var isConnectedToInternet: Boolean = true
    private var searchedCityNames = ArrayList<String>()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_city_search)

        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        val itemInputNameObservable = RxTextView.textChanges(autocomplete_textView)
                .map { inputText: CharSequence -> inputText.isEmpty() || !isValidCityInput(inputText.toString()) }
                .distinctUntilChanged()

        compositeDisposable.add(setupTextInputObserver(itemInputNameObservable))

        setupSearchedCityClickedListener()
    }

    private fun setupSearchedCityClickedListener() {
        cityButton.setOnClickListener {
            processRequestStartUI()

            val searchedCityName = autocomplete_textView.text.toString()
            val geocoder = Geocoder(this, Locale.ENGLISH)

            compositeDisposable.add(setupWeatherDetailsObserver(geocoder, searchedCityName))
        }
    }

    private fun setupTextInputObserver(itemInputNameObservable: Observable<Boolean>): Disposable {
        return itemInputNameObservable.subscribe { inputIsEmpty: Boolean ->
            cityTextInputLayout.setError("Invalid input")
            cityTextInputLayout.setErrorEnabled(inputIsEmpty)
            cityButton?.isEnabled = !inputIsEmpty
        }
    }

    private fun setupWeatherDetailsObserver(geocoder: Geocoder, searchedCityName: String): Disposable {
        return Observable.fromCallable { geocoder.getFromLocationName(searchedCityName, 1) }
                .subscribeOn(Schedulers.io())
                .flatMap { responseAddress: MutableList<Address> ->
                    address = responseAddress[0]
                    viewModel.getWeather(responseAddress[0].latitude, responseAddress[0].longitude)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { weatherResponse: WeatherResponse? ->
                            resolveRequestEndUI()
                            navigateToDetailsActivity(weatherResponse)

                            if (!(searchedCityNames.contains(searchedCityName)))
                                viewModel.addCity(searchedCityName)
                        },
                        { throwable: Throwable? ->
                            resolveRequestEndUI()
                            if (!isConnectedToInternet) {
                                Toast.makeText(this, "You don't have internet coonection, cannot get data offline", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Something went wrong, please try again later", Toast.LENGTH_SHORT).show()
                            }
                        }
                )
    }

    private fun processRequestStartUI() {
        inputLinearLayout.isEnabled = false
        inputLinearLayout.alpha = 0.5f
        progressBar.visibility = View.VISIBLE
    }

    private fun resolveRequestEndUI() {
        inputLinearLayout.isEnabled = true
        inputLinearLayout.alpha = 1f
        progressBar.visibility = View.INVISIBLE
    }

    private fun navigateToDetailsActivity(weatherResponse: WeatherResponse?) {
        val intent = Intent(this, WeatherDetailsActivity::class.java)
        val locationName = address.featureName + ", " + address.countryName
        intent.putExtra("tempBundle", Parcels.wrap(transformToWeatherDetailsDTO(locationName, weatherResponse)))
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        compositeDisposable.add(setupSearchedCitiesObserver())
        compositeDisposable.add(setupInternetConnectionObserver())

    }

    private fun setupSearchedCitiesObserver(): Disposable {
        return viewModel.getCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { citiesList: List<CityEntity> ->
                    searchedCityNames.clear()
                    citiesList.forEach { searchedCityNames.add(it.cityName) }

                    val adapter = ArrayAdapter(this,
                            android.R.layout.simple_dropdown_item_1line, searchedCityNames)

                    autocomplete_textView.setAdapter(adapter)
                    autocomplete_textView.threshold = 0
                }
    }

    private fun setupInternetConnectionObserver(): Disposable {
        return ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { isConnected: Boolean? ->
                            isConnected?.let {
                                when {
                                    !it -> if (isConnectedToInternet)
                                        Toast.makeText(this, "You lost internet connection, for now you can't get data offline.", Toast.LENGTH_SHORT).show()
                                    else -> if (it && !isConnectedToInternet) {
                                        Toast.makeText(this, "Reconnected, now you can get data from internet.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                isConnectedToInternet = isConnected
                            }
                        },
                        { t: Throwable? ->
                            Log.v("ReactiveNetwork", t?.message)
                        }
                )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
