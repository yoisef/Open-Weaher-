package com.alalamiyaalhurra.presenation

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alalamiyaalhurra.data.FakeDataSource
import com.alalamiyaalhurra.weather.domain.models.City
import com.alalamiyaalhurra.weather.presentation.viewmodel.MainViewModel
import com.alalamiyaalhurra.weather.utils.Resource
import com.udacity.project4.locationreminders.CoroutineRule
import com.udacity.project4.locationreminders.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.pauseDispatcher
import kotlinx.coroutines.test.resumeDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(maxSdk = Build.VERSION_CODES.P)
@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = CoroutineRule()

    private lateinit var fakeRepo: FakeDataSource
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun createRepository() {

        fakeRepo = FakeDataSource()
        mainViewModel = MainViewModel(fakeRepo)
    }
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
    fun showLoading_withdata() = runBlocking {
        fakeRepo.deleteAllCities()
        val city = getCity(
            id=1,
            name="Cairo",
            pop = 100
        )
        fakeRepo.saveCityDetails(city)

        mainCoroutineRule.pauseDispatcher()
        mainViewModel.searchCityByName(city.name.toString())

        MatcherAssert.assertThat(mainViewModel.searchedCity.getOrAwaitValue(), CoreMatchers.`is`(Resource.success(city)))


    }

}