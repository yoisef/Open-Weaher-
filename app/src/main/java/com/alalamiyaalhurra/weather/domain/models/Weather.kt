package com.alalamiyaalhurra.weather.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class Weather (

  @field:Json(name = "id"          ) val id          : Int?    = null,
  @field:Json(name = "main"        ) val main        : String? = null,
  @field:Json(name = "description" ) val description : String? = null,
  @field:Json(name = "icon"        ) val icon        : String? = null

)