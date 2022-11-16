package com.alalamiyaalhurra.weather.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alalamiyaalhurra.weather.models.City
import com.alalamiyaalhurra.weather.models.ForecastResponse
import com.soumik.weatherzone.data.repository.local.CityRepository
import com.soumik.weatherzone.data.repository.remote.WeatherRepository
import com.soumik.weatherzone.db.ForecastDatabase
import com.alalamiyaalhurra.weather.utils.Resource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MainViewModel(  private val cityRepository: CityRepository,private val weatherRepository: WeatherRepository,private val forecastDatabase: ForecastDatabase) :ViewModel() {

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

        _forecastByCity.postValue(Resource.loading(null))

    }
    private suspend fun safeWeatherForecastFetch(city :String) {
        _forecastByCity.postValue(Resource.loading(null))

        try {
            val response = weatherRepository.getForecastByName(city)
            _forecastByCity.postValue(handleWeatherResponse(response))

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
                 cityRepository.saveCityDetails(city)
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

            try {
               val city= forecastDatabase.getDao().searchCity(city)


                _searchedCity.postValue(Resource.success(city!!))

            } catch (t:Throwable){
                when(t){
                    is IOException -> _searchedCity.postValue(Resource.error(null,"Network Failure"))
                    else -> _searchedCity.postValue(Resource.error(null,t.localizedMessage))
                }
            }

        }

    }
}

