package com.alalamiyaalhurra.weather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alalamiyaalhurra.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val weatherRepositoryInterface: WeatherRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel( weatherRepository = weatherRepositoryInterface ) as T
    }

}