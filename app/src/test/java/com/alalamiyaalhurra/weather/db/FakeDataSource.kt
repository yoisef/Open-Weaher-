package com.alalamiyaalhurra.weather.db

import com.alalamiyaalhurra.weather.models.City



//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource(var cities: MutableList<City> = mutableListOf()) :
    CityDataSource {

//    TODO: Create a fake data source to act as a double to the real data source
    private var shouldReturnError = false

    //    TODO: Create a fake data source to act as a double to the real data source
    fun setShouldReturnError(shouldReturn: Boolean) {


        this.shouldReturnError = shouldReturn


    }

    override suspend fun getCities(): Result<List<City>> {
        return if (shouldReturnError) {
            Result.Error("An Error with get Data")
        } else {
            Result.Success(cities)
        }
    }

    override suspend fun saveCity(city: City) {
        cities.add(city)
    }

    override suspend fun searchCity(name: String): Result<City> {
        if (shouldReturnError) {
            return Result.Error("An Error with get Data")
        } else {
            val reminder = cities.find{
                it.name == name
            }
            reminder?.let {
                return Result.Success(reminder)
            }
        }
        return Result.Error("There is no reminder with id => $name")
    }



    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

}