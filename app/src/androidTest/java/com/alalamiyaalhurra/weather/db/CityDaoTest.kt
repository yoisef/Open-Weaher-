package com.alalamiyaalhurra.weather.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.alalamiyaalhurra.weather.models.City
import com.soumik.weatherzone.db.ForecastDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Unit test the DAO
@SmallTest
class CityDaoTest {

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

    private fun getCity(): City {
        return City(
            name = "London",
            days = arrayListOf(),
            sunrise = 1,
            sunset = 2,
            id = 5,
            population = 105000000
           )
    }

    @Test
    fun insertCityAndFindById() = runBlockingTest {
        // GIVEN - Insert a reminder.
        val city = getCity()
        database.getDao().insertCity(city)

        // WHEN - Get the reminder by id from the database.
        val loaded = city.name?.let { database.getDao().searchCity(it) }

        // THEN - The loaded data contains the expected values.
        MatcherAssert.assertThat<City>(loaded as City, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loaded.id, `is`(city.id))
        MatcherAssert.assertThat(loaded.name, `is`(city.name))
        MatcherAssert.assertThat(loaded.sunrise, `is`(city.sunrise))
        MatcherAssert.assertThat(loaded.sunset, `is`(city.sunset))
        MatcherAssert.assertThat(loaded.days, `is`(city.days))
        MatcherAssert.assertThat(loaded.population, `is`(city.population))
    }
}