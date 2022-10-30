package com.alalamiyaalhurra.weather.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.alalamiyaalhurra.weather.models.City
import com.soumik.weatherzone.data.repository.local.CityRepository
import com.soumik.weatherzone.db.ForecastDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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
//Medium Test to test the repository
@MediumTest
class CityLocalRepositoryTest {

    // TODO: Add testing implementation to the RemindersLocalRepository.kt
    private lateinit var database: ForecastDatabase
    private lateinit var repository: CityRepository

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

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

    @Before
    fun setup() {
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ForecastDatabase::class.java
        ).allowMainThreadQueries().build()

        repository = CityRepository(database)
    }

    @After
    fun cleanUp() {
        database.close()
    }


    @Test
    fun saveCity_retrievesCity() = runBlocking {
        // GIVEN - A new reminder saved in the database.
        val city = getCity()
        repository.saveCityDetails(city)

        // WHEN  - reminder retrieved by ID.
        val result = city.name?.let { repository.searchCityByName(it) }


        // THEN - Same reminder is returned.
        MatcherAssert.assertThat<City>(result as City, CoreMatchers.notNullValue())


        MatcherAssert.assertThat(result.id, `is`(city.id))
        MatcherAssert.assertThat(result.name, `is`(city.name))
        MatcherAssert.assertThat(result.population, `is`(city.population))
        MatcherAssert.assertThat(result.sunset, `is`(city.sunset))
        MatcherAssert.assertThat(result.sunrise, `is`(city.sunrise))
        MatcherAssert.assertThat(result.days, `is`(city.days))

    }


}