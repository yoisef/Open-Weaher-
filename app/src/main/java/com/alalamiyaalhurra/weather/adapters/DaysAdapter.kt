package com.alalamiyaalhurra.weather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alalamiyaalhurra.weather.databinding.DayRawBinding
import com.alalamiyaalhurra.weather.domain.models.Day
import com.alalamiyaalhurra.weather.utils.DayDiffCallback


class DaysAdapter (var days: ArrayList<Day>) :  RecyclerView.Adapter<DaysAdapter.SearchViewHolder>() {

    private lateinit var binding: DayRawBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {

        binding = DayRawBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchViewHolder(binding)

    }

    override fun getItemCount() = days.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val city = days[position]
        holder.bind(city)

    }


    fun updateDayListItems(newDays: List<Day>) {
        val diffCallback = DayDiffCallback(this.days, newDays)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.days.clear()
        this.days.addAll(newDays)
        diffResult.dispatchUpdatesTo(this)
    }


    inner class SearchViewHolder(private val binding: DayRawBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dayDetails: Day) {
            binding.day = dayDetails
        }


    }
}

