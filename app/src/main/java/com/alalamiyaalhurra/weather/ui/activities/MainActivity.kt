package com.alalamiyaalhurra.weather.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alalamiyaalhurra.weather.R
import com.alalamiyaalhurra.weather.databinding.ActivityMainBinding
import com.alalamiyaalhurra.weather.models.Day
import com.alalamiyaalhurra.weather.models.CityUi
import com.alalamiyaalhurra.weather.utils.unixTimestampToTimeString
import com.alalamiyaalhurra.weather.ui.adapters.DaysAdapter
import com.alalamiyaalhurra.weather.viewmodel.MainViewModel
import com.alalamiyaalhurra.weather.viewmodel.MainViewModelFactory
import com.soumik.weatherzone.data.repository.local.CityRepository
import com.soumik.weatherzone.data.repository.remote.WeatherRepository
import com.soumik.weatherzone.db.ForecastDatabase
import com.soumik.weatherzone.utils.Status

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var weatherRepository :WeatherRepository
    private lateinit var mAdapter : DaysAdapter
    private lateinit var cityRepo : CityRepository
    private lateinit var database:ForecastDatabase



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        database = ForecastDatabase(this)
        weatherRepository = WeatherRepository()
        cityRepo = CityRepository(database)
        val factory = MainViewModelFactory(cityRepository = cityRepo, weatherRepository = weatherRepository, forecastDatabase = database)
        viewModel = ViewModelProvider(this,factory)[MainViewModel::class.java]




        setUpUI()
       setUpObserver()
        handleEvents()

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
            addDays(days)
            notifyDataSetChanged()
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
                    binding.dataLayout.visibility=View.VISIBLE
                    binding.loadingProgress.visibility=View.GONE
                    binding.accurateNotify.visibility=View.GONE

                 resource.data?.city.
                   apply {
                       Log.e("lower","=="+this!!.name!!.lowercase())
                        binding.weather= CityUi(sunRise = this!!.sunrise!!.unixTimestampToTimeString(), sunSet = this!!.sunset!!.unixTimestampToTimeString(), cityName = this.name.toString())
                       this.days= resource.data?.day
                        Log.e("city","==${this.name}")
                        viewModel.insertCity( city = this)
                        resource.data?.let { updateData(it.day) }
                    }



                }
                Status.ERROR->{
                    binding.loadingProgress.visibility=View.GONE
                    binding.dataLayout.visibility=View.VISIBLE

                    viewModel.searchCityByName( city = binding.citTxtField.text.toString().trim())



                }
                Status.LOADING->{

                    binding.loadingProgress.visibility=View.VISIBLE
                    binding.guidingText.visibility=View.GONE
                }
            }
        })


        viewModel.searchedCity.observe(this, Observer { city ->

            when(city.status)
            {
                Status.SUCCESS ->{
                    binding.dataLayout.visibility=View.VISIBLE
                    binding.errorLayout.visibility=View.GONE
                    binding.accurateNotify.visibility=View.VISIBLE



                    city.let {
                        it.data!!.days?.let { it1 -> updateData(it1) }
                        binding.weather= CityUi(sunRise = it.data.sunrise!!.unixTimestampToTimeString(), sunSet = it.data.sunset!!.unixTimestampToTimeString(), cityName = it.data.name.toString())

                    }
                }
                Status.ERROR ->   {
                    binding.errorLayout.visibility=View.VISIBLE
                    binding.guidingText.visibility=View.GONE
                    binding.dataLayout.visibility=View.GONE

                }

                Status.LOADING ->    {

                    binding.guidingText.visibility=View.GONE

                }

            }
        })
    }
}