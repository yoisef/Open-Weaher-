package com.alalamiyaalhurra.weather.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.alalamiyaalhurra.weather.R
import com.bumptech.glide.Glide



@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, iconUrl: String?) {
    val context = imageView.context
    val fullUrl="https://openweathermap.org/img/w/${iconUrl}.png"
    Log.e("imageUrl","==$fullUrl")
    Glide.with(context)
        .load(fullUrl)
        .placeholder(R.drawable.ic_launcher_foreground)
        .into(imageView)

}


