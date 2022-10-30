package com.alalamiyaalhurra.weather.ui

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.alalamiyaalhurra.weather.db.CityDataSource
import com.alalamiyaalhurra.weather.db.FakeDataSource
import com.alalamiyaalhurra.weather.models.City
import com.alalamiyaalhurra.weather.util.EspressoIdlingResource
import com.alalamiyaalhurra.weather.utils.unixTimestampToTimeString
import com.alalamiyaalhurra.weather.viewmodel.MainViewModel
import com.udacity.project4.util.DataBindingIdlingResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
//UI Testing
@MediumTest
class MainActivityTest {

    private lateinit var cityDataSource: CityDataSource
    private lateinit var appContext: Application
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    private val city = City(1, "alex", "EG", 105000000, 1,2,3)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {

        appContext = ApplicationProvider.getApplicationContext()


        runBlocking {
            cityDataSource.saveCity(city)
        }
    }

    @Before
    fun registerIdlingResource(): Unit = IdlingRegistry.getInstance().run{
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }


    @After
    fun unregisterIdlingResource(): Unit = IdlingRegistry.getInstance().run {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun display_error_layout_when_no_result() {
        runBlocking {
            cityDataSource.searchCity("nothing")
        }



    }

    @Test
    fun savedReminders() {

        Espresso.onView(ViewMatchers.withText(city.name)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            ))
        Espresso.onView(ViewMatchers.withText(city.sunrise!!.unixTimestampToTimeString()))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(city.sunset!!.unixTimestampToTimeString()))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun search_click_save_new_city() {


    }

}