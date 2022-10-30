package com.soumik.weatherzone.data.repository.local

import com.alalamiyaalhurra.weather.models.City
import com.alalamiyaalhurra.weather.models.Day
import com.soumik.weatherzone.db.CityDao
import com.soumik.weatherzone.db.ForecastDatabase


class CityRepository (private val database: ForecastDatabase) {

    suspend fun saveCityDetails(city: City) =database.getDao().insertCity(city)
    fun getSavedCities() = database.getDao().getSavedCities()

    suspend fun searchCityByName(name :String)=database.getDao().searchCity(name)
}