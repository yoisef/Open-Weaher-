package com.alalamiyaalhurra.weather.presentation.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alalamiyaalhurra.weather.R
import com.alalamiyaalhurra.weather.databinding.ActivityMainBinding
import com.alalamiyaalhurra.weather.domain.models.Day
import com.alalamiyaalhurra.weather.domain.models.CityUi
import com.alalamiyaalhurra.weather.utils.unixTimestampToTimeString
import com.alalamiyaalhurra.weather.adapters.DaysAdapter
import com.alalamiyaalhurra.weather.presentation.viewmodel.MainViewModel
import com.soumik.weatherzone.utils.Status

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var mAdapter : DaysAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        setUpUI()
        handleEvents()
        setUpObserver()

    }

    private fun handleEvents()
    {
        binding.searchBtn.setOnClickListener {

           viewModel.getForecastByCity(binding.citTxtField.text.toString().trim())
            hideKeyboard()
        }

        doSearch(binding.citTxtField)

        binding.retyBtn.setOnClickListener {
            viewModel.getForecastByCity(binding.citTxtField.text.toString().trim())


        }
    }
    private fun setUpUI(){

        binding.recyclerDayTimes.layoutManager = LinearLayoutManager(this)
        mAdapter = DaysAdapter(arrayListOf())
        binding.recyclerDayTimes.adapter =mAdapter
    }
    private fun doSearch(search: EditText){
        search.setOnEditorActionListener(TextView.OnEditorActionListener{ _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                hideKeyboard()
                viewModel.getForecastByCity(search.text.toString().trim())


                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun updateData(days: List<Day>) {
        mAdapter.apply {
            updateDayListItems(days)

        }
    }

    private fun hideKeyboard() {
        val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
    fun setUpObserver()
    {
        viewModel.forecastByCity.observe(this, Observer { resource ->


            when(resource.status)
            {
                Status.SUCCESS->{
                   handleRemoteSuccess()
                    Log.e("status","==successremote"+resource.status)

                    resource.data?.city.
                   apply {
                       Log.e("lower","=="+this!!.name!!.lowercase())
                        binding.weather= CityUi(sunRise = this!!.sunrise!!.unixTimestampToTimeString(), sunSet = this!!.sunset!!.unixTimestampToTimeString(), cityName = this.name.toString(), country =this.country.toString() )
                       this.days= resource.data?.list
                        Log.e("city","==${this.name}")
                        viewModel.insertCity( city = this)
                        resource.data?.let { updateData(it.list) }
                    }



                }
                Status.ERROR->{
                    Log.e("status","==errorremote"+resource.status)


                    viewModel.searchCityByName( city = binding.citTxtField.text.toString().trim())



                }
                Status.LOADING->{
                    Log.e("status","==loadingremote"+resource.status)

                   handleLoading()

                }
            }
        })


        viewModel.searchedCity.observe(this, Observer { city ->

            Log.e("status","=="+city.status)
            when(city.status)
            {
                Status.SUCCESS ->{
                    handleLocalSuccess()
                    Log.e("status","==successsearch"+city.status)


                    city.let {
                        it.data!!.days?.let { it1 -> updateData(it1) }
                        binding.weather= CityUi(sunRise = it.data.sunrise!!.unixTimestampToTimeString(), sunSet = it.data.sunset!!.unixTimestampToTimeString(), cityName = it.data.name.toString(), country =it.data.country.toString())

                    }
                }
                Status.ERROR ->   {
                    Log.e("status","==errorsearch"+city.status)

                    handleFailure()

                }

                Status.LOADING ->    {
                    Log.e("status","==loadingsearch"+city.status)

                    handleLoading()


                }

            }
        })
    }

    fun handleRemoteSuccess()
    {
        binding.dataLayout.visibility=View.VISIBLE
        binding.accurateNotify.visibility=View.GONE
        binding.loadingProgress.visibility=View.GONE
        binding.guidingText.visibility=View.GONE
        binding.errorLayout.visibility=View.GONE


    }
    fun handleLocalSuccess()
    {
        binding.dataLayout.visibility=View.VISIBLE
        binding.accurateNotify.visibility=View.VISIBLE
        binding.loadingProgress.visibility=View.GONE
        binding.guidingText.visibility=View.GONE
        binding.errorLayout.visibility=View.GONE


    }

    fun handleFailure()
    {
        binding.dataLayout.visibility=View.GONE
        binding.accurateNotify.visibility=View.GONE
        binding.loadingProgress.visibility=View.GONE
        binding.guidingText.visibility=View.GONE
        binding.errorLayout.visibility=View.VISIBLE
    }

    fun handleLoading()
    {
        binding.loadingProgress.visibility=View.VISIBLE
        binding.guidingText.visibility=View.GONE
        binding.dataLayout.visibility=View.GONE
        binding.errorLayout.visibility=View.GONE
        binding.accurateNotify.visibility=View.GONE



    }
}