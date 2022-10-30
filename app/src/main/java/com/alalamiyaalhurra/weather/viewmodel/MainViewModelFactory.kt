package com.alalamiyaalhurra.weather.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.soumik.weatherzone.data.repository.local.CityRepository
import com.soumik.weatherzone.data.repository.remote.WeatherRepository
import com.soumik.weatherzone.db.ForecastDatabase

class MainViewModelFactory  constructor( val cityRepository: CityRepository,val weatherRepository: WeatherRepository,val forecastDatabase: ForecastDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel( cityRepository = cityRepository, weatherRepository = weatherRepository, forecastDatabase = forecastDatabase ) as T
    }

}