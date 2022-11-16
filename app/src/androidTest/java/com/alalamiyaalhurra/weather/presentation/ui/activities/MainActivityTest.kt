package com.alalamiyaalhurra.weather.presentation.ui.activities

import android.app.Application
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.alalamiyaalhurra.weather.domain.repository.WeatherRepository
import com.alalamiyaalhurra.weather.presentation.ui.util.DataBindingIdlingResource
import com.alalamiyaalhurra.weather.presentation.ui.util.monitorActivity
import com.alalamiyaalhurra.weather.utils.EspressoIdlingResource
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest{

    private lateinit var repository: WeatherRepository
    private lateinit var appContext: Application
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }


    @Test
    fun type_city_in_search_field() = runTest {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)
        Espresso.onView(withId(com.alalamiyaalhurra.weather.R.id.cit_txt_field)).perform(ViewActions.typeText("London"))
        Espresso.closeSoftKeyboard()
        Espresso.onView(withId(com.alalamiyaalhurra.weather.R.id.search_btn)).perform(ViewActions.click())




    }


}