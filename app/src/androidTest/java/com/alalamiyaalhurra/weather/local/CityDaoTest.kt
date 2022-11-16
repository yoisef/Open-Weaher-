package com.alalamiyaalhurra.weather.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import com.alalamiyaalhurra.weather.data.db.ForecastDatabase
import com.alalamiyaalhurra.weather.domain.models.City

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Test

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Unit test the DAO
@SmallTest

class CityDaoTest {

//    TODO: Add testing implementation to the RemindersDao.kt

    private lateinit var database: ForecastDatabase

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ForecastDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() = database.close()


     fun getCity( id:Int,name :String , pop : Int): City {
        return City(
            id = id,
            name = name,
            sunrise = 1,
            sunset = 2,
            population = pop
           )
    }

    @Test
    fun insertCityAndFindByName() = runTest {
        // GIVEN - Insert a reminder.
        val city = getCity(1,"EG",100)
        database.getDao().insertCity(city)

        // WHEN - Get the reminder by id from the database.
        val loaded = database.getDao().searchCity(city.name.toString())

        // THEN - The loaded data contains the expected values.
        assertThat<City>(loaded as City, notNullValue())
        assertThat(loaded.id, `is`(city.id))
        assertThat(loaded.name, `is`(city.name))
        assertThat(loaded.sunrise, `is`(city.sunrise))
        assertThat(loaded.sunset, `is`(city.sunset))
        assertThat(loaded.population, `is`(city.population))
    }

    @Test
    fun insertNofCityAnd_GetCityN() = runTest {
        // GIVEN - Insert a reminder.
        val city = getCity(1,"EG",10)
        val city1 = getCity(2,"SA",20)
        val city2 = getCity(3,"FR",8)

        database.getDao().deleteAllCities()
        val num1= database.getDao().getCities().size

        assertThat(num1,`is`(0))

        database.getDao().insertCity(city)
        database.getDao().insertCity(city1)
        database.getDao().insertCity(city2)

        val num= database.getDao().getCities().size

        assertThat(num,`is`(3))
        // WHEN - Get the reminder by id from the database.

    }

}
