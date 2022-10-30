package com.soumik.weatherzone.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alalamiyaalhurra.weather.models.Article
import com.alalamiyaalhurra.weather.models.City
import com.alalamiyaalhurra.weather.models.Day


@Dao
interface CityDao {


    @Query("SELECT * FROM cities")
    fun getSavedCities() :LiveData<List<City>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(cities :City)




    @Query("SELECT * FROM cities WHERE name LIKE :name || '%'")
    suspend fun searchCity(name:String):City
}