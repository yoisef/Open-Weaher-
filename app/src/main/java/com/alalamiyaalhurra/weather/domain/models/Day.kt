package com.alalamiyaalhurra.weather.domain.models

import androidx.room.Entity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class Day (

    @field:Json(name = "dt"         ) val dt         : Int?               = null,
    @field:Json(name = "main"       ) val main       : Main?              = Main(),
    @field:Json(name = "weather"    ) val weather    : List<Weather> = arrayListOf(),
    @field:Json(name = "clouds"     ) val clouds     : Clouds?            = Clouds(),
    @field:Json(name = "wind"       ) val wind       : Wind?              = Wind(),
    @field:Json(name = "visibility" ) val visibility : Int?               = null,
    @field:Json(name = "pop"        ) val pop        : Double?               = null,
    @field:Json(name = "sys"        ) val sys        : Sys?               = Sys(),
    @field:Json(name = "dt_txt"     ) val dtTxt      : String?            = null

)