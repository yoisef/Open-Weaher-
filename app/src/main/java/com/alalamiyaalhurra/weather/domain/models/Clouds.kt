package com.alalamiyaalhurra.weather.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class Clouds (

  @field:Json(name = "all" ) val all : Int? = null

)