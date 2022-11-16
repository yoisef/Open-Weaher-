package com.alalamiyaalhurra.weather.data.db

import androidx.room.TypeConverter
import com.alalamiyaalhurra.weather.domain.models.Coord
import com.alalamiyaalhurra.weather.domain.models.Day
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class Converters {
    @TypeConverter
    fun toCoordJson(meaning: Coord): String {
        val gson = Gson()
        return gson.toJson(meaning)
    }

    @TypeConverter
    fun toCoordObject(json: String): Coord {
        val gson = Gson()
        val coordType = object : TypeToken<Coord>() {}.type


        return gson.fromJson<Coord>(json, coordType)

    }

    @TypeConverter
    fun toListDayJson(meaning: List<Day>): String {
        val gson = Gson()
        return gson.toJson(meaning)
    }

    @TypeConverter
    fun toListDasObject(json: String): List<Day> {
        val gson = Gson()
        val listtype = object : TypeToken<kotlin.collections.List<Day>>() {}.type


        return gson.fromJson<kotlin.collections.List<Day>>(json, listtype)

    }




}