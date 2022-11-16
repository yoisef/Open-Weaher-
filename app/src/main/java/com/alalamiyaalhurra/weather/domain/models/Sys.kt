package com.alalamiyaalhurra.weather.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class Sys (

  @field:Json(name = "pod" ) val pod : String? = null

)