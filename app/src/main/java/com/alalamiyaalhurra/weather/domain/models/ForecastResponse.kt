package com.alalamiyaalhurra.weather.domain.models

import androidx.room.Entity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class ForecastResponse (

    @field:Json(name = "cod") var cod     : String?         = null,
    @field:Json(name = "message" ) var message : Int?            = null,
    @field:Json(name = "cnt") var cnt     : Int?            = null,
    @field:Json(name = "list") var list    : List<Day> = arrayListOf(),
    @field:Json(name = "city") var city    : City?           = City()

)