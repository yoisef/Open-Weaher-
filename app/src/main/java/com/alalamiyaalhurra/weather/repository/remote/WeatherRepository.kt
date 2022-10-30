package com.soumik.weatherzone.data.repository.remote

import com.alalamiyaalhurra.weather.network.RetrofitClient



class WeatherRepository {

    suspend fun getForecastByName(cityName:String) = RetrofitClient.api.getWeatherByLocation(cityName)

}