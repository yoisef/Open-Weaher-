package com.alalamiyaalhurra.weather.network


import com.alalamiyaalhurra.weather.BuildConfig
import com.alalamiyaalhurra.weather.utils.Unit_Type
import okhttp3.Interceptor
import okhttp3.Response

class QueryParameterAddInterceptor:Interceptor {



    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.newBuilder()
            .addQueryParameter("appid", BuildConfig.API_KEY)
            .addQueryParameter("units",Unit_Type)

            .build()

        val request = chain.request().newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}