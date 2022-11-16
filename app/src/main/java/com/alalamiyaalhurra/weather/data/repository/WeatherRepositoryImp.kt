package com.alalamiyaalhurra.weather.data.repository

import androidx.lifecycle.LiveData
import com.alalamiyaalhurra.weather.data.db.CityDao

import com.alalamiyaalhurra.weather.domain.repository.WeatherRepository
import com.alalamiyaalhurra.weather.domain.models.City
import com.alalamiyaalhurra.weather.domain.models.ForecastResponse
import com.alalamiyaalhurra.weather.network.EndPoint
import com.alalamiyaalhurra.weather.utils.Resource
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImp @Inject constructor(private val cityDao: CityDao, private val endPoint: EndPoint?) :
    WeatherRepository {

    override suspend fun saveCityDetails(city: City) =cityDao.insertCity(city)


    override suspend fun getSavedCities(): LiveData<List<City>> {
        return cityDao.getSavedCities()
    }

    override suspend fun getCities(): Resource<List<City>> {
        return  try {
           Resource.success(cityDao.getCities()!!)
        } catch (ex: Exception) {
            Resource.error(null,ex.localizedMessage)
        }
    }


    override suspend fun searchCityByName(name: String): Resource<City> {

        return  try {
            Resource.success(cityDao.searchCity(name)!!)
        } catch (ex: Exception) {
            Resource.error(null,"City Not Found")
        }



    }



    override suspend fun getForecastByName(cityName: String) : Response<ForecastResponse>{


         return  endPoint!!.getWeatherByLocation(cityName)



    }

    override suspend fun deleteAllCities() =cityDao.deleteAllCities()


}