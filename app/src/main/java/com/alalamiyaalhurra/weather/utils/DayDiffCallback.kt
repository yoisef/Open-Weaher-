package com.alalamiyaalhurra.weather.utils

import androidx.recyclerview.widget.DiffUtil
import com.alalamiyaalhurra.weather.domain.models.Day

class DayDiffCallback(private val mOldDayList: List<Day>, private val mNewDayList: List<Day>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldDayList.size
    }

    override fun getNewListSize(): Int {
        return mNewDayList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldDayList[oldItemPosition].weather[0].id === mNewDayList[newItemPosition].weather[0].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val (_, _, weather) = mOldDayList[oldItemPosition]
        val newDay = mNewDayList[newItemPosition]
        return weather[0].main == newDay.weather[0].main
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}