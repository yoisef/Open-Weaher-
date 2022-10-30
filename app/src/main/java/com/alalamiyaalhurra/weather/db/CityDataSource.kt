package com.alalamiyaalhurra.weather.db

import com.alalamiyaalhurra.weather.models.City

/**
 * Main entry point for accessing reminders data.
 */
interface CityDataSource {
    suspend fun getCities(): Result<List<City>>
    suspend fun saveCity(city: City)
    suspend fun searchCity(name: String): Result<City>
}