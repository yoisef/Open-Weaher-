package com.alalamiyaalhurra.weather.network

import com.alalamiyaalhurra.weather.domain.models.ForecastResponse

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



interface EndPoint {

    @GET("forecast")
   suspend fun getWeatherByLocation(@Query("q") cityName:String ):Response<ForecastResponse>


}