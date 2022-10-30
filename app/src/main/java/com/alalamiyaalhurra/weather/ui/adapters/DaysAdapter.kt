package com.alalamiyaalhurra.weather.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alalamiyaalhurra.weather.databinding.DayRawBinding
import com.alalamiyaalhurra.weather.models.Day



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

    fun addDays(days: List<Day>) {
        this.days.apply {
            clear()
            addAll(days)
        }
    }

    fun clearData() {
        days.clear()
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(private val binding: DayRawBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dayDetails: Day) {
            binding.day = dayDetails
        }


    }
}

