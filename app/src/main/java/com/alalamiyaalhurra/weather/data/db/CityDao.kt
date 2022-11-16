package com.alalamiyaalhurra.weather.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alalamiyaalhurra.weather.domain.models.Article
import com.alalamiyaalhurra.weather.domain.models.City
import com.alalamiyaalhurra.weather.domain.models.Day
import com.alalamiyaalhurra.weather.utils.Resource


@Dao
interface CityDao {


    @Query("SELECT * FROM cities")
    fun getSavedCities() :LiveData<List<City>>

    @Query("SELECT * FROM cities")
    suspend fun getCities() : List<City>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(cities : City)


    @Query("DELETE FROM cities")
    suspend fun deleteAllCities()

    @Query("SELECT * FROM cities WHERE name LIKE :name || '%'")
    suspend fun searchCity(name:String): City
}