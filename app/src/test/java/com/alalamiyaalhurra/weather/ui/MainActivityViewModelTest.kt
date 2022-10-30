package com.alalamiyaalhurra.weather.ui

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alalamiyaalhurra.weather.db.FakeDataSource
import com.alalamiyaalhurra.weather.models.City
import com.alalamiyaalhurra.weather.viewmodel.MainViewModel
import com.soumik.weatherzone.data.repository.local.CityRepository
import com.soumik.weatherzone.db.ForecastDatabase
import com.udacity.project4.locationreminders.CoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.pauseDispatcher
import kotlinx.coroutines.test.resumeDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(maxSdk = Build.VERSION_CODES.P)
@ExperimentalCoroutinesApi
class MainActivityViewModelTest {

    private lateinit var database: ForecastDatabase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = CoroutineRule()

    private lateinit var fakeRepo: FakeDataSource
    private lateinit var cityListViewModel: MainViewModel

    @Before
    fun createRepository() {

        fakeRepo = FakeDataSource()
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ForecastDatabase::class.java
        ).allowMainThreadQueries().build()
     //   cityListViewModel = MainViewModel(,"","")

    }
    @Test
    fun loadDaysWhenRemindersAreUnavailable() = runBlockingTest {

        fakeRepo.setShouldReturnError(true)
        cityListViewModel.searchCityByName("cairo")

    }


    private fun getCity(): City {
        return City(
            name = "Cairo",
            days = arrayListOf(),
            sunrise = 1,
            sunset = 2,
            id = 5,
            population = 105000000
        )
    }
    @Test
    fun showLoading_withdata() = runBlocking {
        val city =getCity()
        city.name?.let { fakeRepo.searchCity(it) }

        mainCoroutineRule.pauseDispatcher()


    }



}