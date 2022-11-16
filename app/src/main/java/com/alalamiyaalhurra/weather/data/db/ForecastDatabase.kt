package com.alalamiyaalhurra.weather.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alalamiyaalhurra.weather.data.db.CityDao
import com.alalamiyaalhurra.weather.data.db.Converters
import com.alalamiyaalhurra.weather.domain.models.City


@Database(entities = [City::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ForecastDatabase : RoomDatabase() {

    abstract fun getDao(): CityDao






}