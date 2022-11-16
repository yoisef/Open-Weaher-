package com.alalamiyaalhurra.weather.di

import android.content.Context
import androidx.room.Room
import com.alalamiyaalhurra.weather.utils.Database_Name
import com.alalamiyaalhurra.weather.data.db.CityDao
import com.alalamiyaalhurra.weather.data.db.ForecastDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {



        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext appContext: Context): ForecastDatabase {
            return Room.databaseBuilder(
                appContext,
                ForecastDatabase::class.java,
                Database_Name
            ).build()
        }

        @Provides
        fun provideLogDao(database: ForecastDatabase): CityDao {
            return database.getDao()
        }


}