package com.alalamiyaalhurra.weather.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class Main (

  @field:Json(name = "temp"       ) val temp      : String? = null,
  @field:Json(name = "feels_like" ) val feels_like : Double? = null,
  @field:Json(name = "temp_min"   ) val temp_min   : Double? = null,
  @field:Json(name = "temp_max"   ) val temp_max   : Double? = null,
  @field:Json(name = "pressure"   ) val pressure  : Int?    = null,
  @field:Json(name = "sea_level"  ) val seaLevel  : Int?    = null,
  @field:Json(name = "grnd_level" ) val grndLevel : Int?    = null,
  @field:Json(name = "humidity"   ) val humidity  : Int?    = null,
  @field:Json(name = "temp_kf"    ) val tempKf    : Double? = null

)