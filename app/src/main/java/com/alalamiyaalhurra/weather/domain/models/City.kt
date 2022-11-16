package com.alalamiyaalhurra.weather.domain.models
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
@Entity(tableName = "cities")
data class City (


    @PrimaryKey(autoGenerate = false)
  @field:Json(name = "id" ) var id         : Int?    = null,
    @field:Json(name="name" ) var name       : String? = null,
    @field:Json(name = "country") var country    : String? = null,
    @field:Json(name = "population") var population : Int?    = null,
    @field:Json(name = "timezone" ) var timezone   : Int?    = null,
    @field:Json(name = "sunrise") var sunrise    : Int?    = null,
    @field:Json(name = "sunset" ) var sunset     : Int?    = null,
    @field:Json(name = "coord"  ) var coord      : Coord?  = Coord(),
    @field:Json(name = "days"  ) var days      : List<Day>?  = arrayListOf(),




    ){


}

