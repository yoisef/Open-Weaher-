package com.alalamiyaalhurra.weather.di

import com.alalamiyaalhurra.weather.data.db.CityDao
import com.alalamiyaalhurra.weather.data.repository.WeatherRepositoryImp

import com.alalamiyaalhurra.weather.domain.repository.WeatherDataSource
import com.alalamiyaalhurra.weather.domain.repository.WeatherRepository
import com.alalamiyaalhurra.weather.utils.BASE_URL
import com.alalamiyaalhurra.weather.network.EndPoint
import com.alalamiyaalhurra.weather.network.QueryParameterAddInterceptor
import com.alalamiyaalhurra.weather.utils.Resource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor{
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(QueryParameterAddInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()


    }


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): EndPoint {
        return retrofit.create(EndPoint::class.java)
    }














}