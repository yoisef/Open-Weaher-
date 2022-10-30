package com.soumik.weatherzone.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alalamiyaalhurra.weather.db.Converters
import com.alalamiyaalhurra.weather.models.Article
import com.alalamiyaalhurra.weather.models.City
import com.alalamiyaalhurra.weather.models.Day
import com.alalamiyaalhurra.weather.utils.DB_NAME
import com.alalamiyaalhurra.weather.utils.Database_Name


@Database(entities = [City::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ForecastDatabase : RoomDatabase() {

    abstract fun getDao(): CityDao




        companion object {
            @Volatile
            private var instance: ForecastDatabase? = null
            private val LOCK = Any()

            operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
                instance ?: createDatabase(context).also { instance = it }
            }

            private fun createDatabase(context: Context) =
                Room.databaseBuilder(
                    context.applicationContext,
                    ForecastDatabase::class.java,
                    Database_Name
                )

                    .build()
        }

}