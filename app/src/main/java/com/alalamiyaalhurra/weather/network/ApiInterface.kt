package com.soumik.weatherzone.network

import com.alalamiyaalhurra.weather.models.ForecastResponse

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



interface ApiInterface {

    @GET("forecast")
   suspend fun getWeatherByLocation(@Query("q") cityName:String ):Response<ForecastResponse>


}