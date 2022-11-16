package com.alalamiyaalhurra.data

import androidx.lifecycle.LiveData
import com.alalamiyaalhurra.weather.domain.repository.WeatherRepository
import com.alalamiyaalhurra.weather.domain.models.City
import com.alalamiyaalhurra.weather.domain.models.ForecastResponse
import com.alalamiyaalhurra.weather.utils.Resource
import retrofit2.Response

class FakeDataSource(var cities: MutableList<City> = mutableListOf()) : WeatherRepository {

    private var shouldReturnError = false

    override suspend fun saveCityDetails(city: City) {
        cities.add(city)
    }

    override suspend fun getSavedCities(): LiveData<List<City>> {
        TODO("Not yet implemented")

    }

    override suspend fun getCities(): Resource<List<City>> {
        return if (shouldReturnError) {
            Resource.error(null,"An Error with get Data")
        } else {
           Resource.success(cities)
        }
    }

    override suspend fun searchCityByName(name: String): Resource<City> {

        if (shouldReturnError) {
            return Resource.error(null,"An Error with get Data")
        } else {
            val reminder = cities.find{
                it.name == name
            }
            reminder?.let {
                return Resource.success(reminder)
            }
        }
        return Resource.error(null,"There is no city with name => $name")
    }

    override suspend fun getForecastByName(cityName: String): Response<ForecastResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllCities() {
        cities.clear()
    }
}