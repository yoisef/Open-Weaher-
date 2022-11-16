package com.alalamiyaalhurra.weather.domain.repository

import androidx.lifecycle.LiveData
import com.alalamiyaalhurra.weather.domain.models.City
import com.alalamiyaalhurra.weather.domain.models.ForecastResponse
import com.alalamiyaalhurra.weather.utils.Resource
import retrofit2.Response

interface WeatherRepository  {

     suspend fun saveCityDetails(city: City)
     suspend fun getSavedCities(): LiveData<List<City>>
     suspend fun getCities(): Resource<List<City>>

     suspend fun searchCityByName(name :String): Resource<City>

     suspend fun getForecastByName(cityName:String): Response<ForecastResponse>
     suspend fun deleteAllCities()

}