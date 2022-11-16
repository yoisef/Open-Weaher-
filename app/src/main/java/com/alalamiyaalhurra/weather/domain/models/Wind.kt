package com.alalamiyaalhurra.weather.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class Wind (

  @field:Json(name = "speed" ) val speed : Double? = null,
  @field:Json(name = "deg"   ) val deg   : Int?    = null,
  @field:Json(name = "gust"  ) val gust  : Double? = null

)