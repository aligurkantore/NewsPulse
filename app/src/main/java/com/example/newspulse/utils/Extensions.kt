package com.example.newspulse.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun ImageView.loadImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .into(this)
}

fun String.toUnixTimestamp(): Long {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val date = dateFormat.parse(this)
    return date?.time ?: 0
}

fun Long.formatTimestamp(pattern: String = "dd MM yyyy"): String {
    val dateFormatter = SimpleDateFormat(pattern, Locale.getDefault())
    return dateFormatter.format(this)
}


fun View.show(){
    visibility = View.VISIBLE
}
fun View.hide(){
    visibility = View.GONE
}

fun showToast(context : Context){
    Toast.makeText(context, "You entered incomplete information", Toast.LENGTH_SHORT).show()
}

/*
fun Int.toFormattedString(): String {
    return String.format(Locale.getDefault(), "%,d", this)
}
 */