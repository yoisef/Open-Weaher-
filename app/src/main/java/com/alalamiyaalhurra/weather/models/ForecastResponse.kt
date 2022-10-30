package com.alalamiyaalhurra.weather.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class ForecastResponse (

  @field:Json(name = "cod") var cod     : String?         = null,
  @field:Json(name = "message" ) var message : Int?            = null,
  @field:Json(name = "cnt") var cnt     : Int?            = null,
  @field:Json(name = "list") var day    : List<Day> = arrayListOf(),
  @field:Json(name = "city") var city    : City?           = City()

)