package com.alalamiyaalhurra.weather.network


import com.alalamiyaalhurra.weather.utils.BASE_URL
import com.soumik.weatherzone.network.ApiInterface
import com.soumik.weatherzone.network.QueryParameterAddInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class RetrofitClient {

    companion object {

        private val retrofit by lazy {
            val httpClient = OkHttpClient.Builder().addInterceptor(QueryParameterAddInterceptor()).apply {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)) }.build()

            Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(httpClient)
                .build()
        }

        val api by lazy {
            retrofit.create(ApiInterface::class.java)
        }
    }
}