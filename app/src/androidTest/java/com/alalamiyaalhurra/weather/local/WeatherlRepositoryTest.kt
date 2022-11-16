package com.alalamiyaalhurra.weather.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.alalamiyaalhurra.weather.data.db.ForecastDatabase
import com.alalamiyaalhurra.weather.data.repository.WeatherRepositoryImp
import com.alalamiyaalhurra.weather.domain.models.City

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Medium Test to test the repository
@MediumTest
class WeatherlRepositoryTest {

    private val city1 = City(1, "Egypt")
    private val city2 = City(2, "Emairat")
    private val city3 = City(3, "Bahrain")
    private val remoteTasks = listOf(city1, city2).sortedBy { it.id }
    private val localTasks = listOf(city3).sortedBy { it.id }
    private lateinit var database: ForecastDatabase
    private lateinit var repository: WeatherRepositoryImp




    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private fun getCity(): City {
        return City(
            id = 1,
            name = "Egypt",
            sunrise = 1,
            sunset = 2,
            population = 100,
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

        repository= WeatherRepositoryImp(database.getDao(),null)
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
        val result = repository.searchCityByName(city.name.toString())


        // THEN - Same reminder is returned.
        assertThat(result.status ==  com.soumik.weatherzone.utils.Status.SUCCESS, `is`(true))


        assertThat(result.data!!.id, `is`(city.id))
        assertThat(result.data!!.name, `is`(city.name))
        assertThat(result.data!!.sunrise, `is`(city.sunrise))
        assertThat(result.data!!.sunset, `is`(city.sunset))
        assertThat(result.data!!.population, `is`(city.population))
    }

    @Test
    fun deleteAllReminders_getReminderById() = runBlocking {
        val city = getCity()
        repository.saveCityDetails(city)
        repository.deleteAllCities()

        val result = repository.searchCityByName(city.name.toString())


        assertThat(result.status == com.soumik.weatherzone.utils.Status.ERROR, `is`(true))
        assertThat(result.message, `is`("City Not Found"))

    }
}