package com.konradszewczuk.weatherapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val weatherApplication: WeatherApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = weatherApplication

}
