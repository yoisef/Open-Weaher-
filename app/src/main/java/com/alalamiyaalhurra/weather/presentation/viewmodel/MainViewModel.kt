package com.alalamiyaalhurra.weather.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alalamiyaalhurra.weather.domain.repository.WeatherRepository
import com.alalamiyaalhurra.weather.domain.models.City
import com.alalamiyaalhurra.weather.domain.models.ForecastResponse
import com.alalamiyaalhurra.weather.utils.Resource

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel

class MainViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :ViewModel() {


    private var _forecastByCity = MutableLiveData<Resource<ForecastResponse>>()

    val forecastByCity:LiveData<Resource<ForecastResponse>>
    get() = _forecastByCity

    private var _searchedCity = MutableLiveData<Resource<City>>()

    val searchedCity:LiveData<Resource<City>>
        get() = _searchedCity

    fun getForecastByCity(city:String)
    {
        viewModelScope.launch(IO){
            safeWeatherForecastFetch(city)
        }


    }
    private suspend fun safeWeatherForecastFetch(city :String) {
        _forecastByCity.postValue(Resource.loading(null))

        try {
            val response = weatherRepository.getForecastByName(city)
            Log.e("response",response.isSuccessful.toString())
            if (response.isSuccessful)
            {
               _forecastByCity.postValue(handleWeatherResponse(response))

            }else{
               _forecastByCity.postValue(Resource.error(null,response.errorBody().toString()))

            }

        } catch (t:Throwable){
            when(t){
                is IOException -> _forecastByCity.postValue(Resource.error(null,"Network Failure"))
                else -> _forecastByCity.postValue(Resource.error(null,t.localizedMessage))
            }
        }
    }
    private fun handleWeatherResponse(response: Response<ForecastResponse>): Resource<ForecastResponse>? {
        return if (response.isSuccessful) Resource.success(response.body())
        else Resource.error(null,"Error: ${response.errorBody()}")
    }

     fun insertCity(city: City) {

         try {
             viewModelScope.launch(IO) {
                 weatherRepository.saveCityDetails(city)
                 Log.i("Insert ", "Success")

             }
         } catch (e: Exception) {
             e.stackTrace
             Log.i("Insert ", "error${e.localizedMessage}")


         }
     }

     fun searchCityByName(city: String)
    {
        viewModelScope.launch(IO) {
            val res= weatherRepository.searchCityByName(city)
            Log.e("statusis","st=="+res.status.toString())
            _searchedCity.postValue(res!!)


        }

    }


}

