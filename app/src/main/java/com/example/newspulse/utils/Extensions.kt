package com.example.newspulse.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.newspulse.R
import java.text.SimpleDateFormat
import java.util.Locale

fun ImageView.loadImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.gallery)
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


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.inVisible() {
    visibility = View.INVISIBLE
}

infix fun View.visibleIf(b: Boolean) {
    if (b) visible() else gone()
}

infix fun View.inVisibleIf(b: Boolean){
    if (b) inVisible() else visible()
}

infix fun View.goneIf(b: Boolean){
    if (b) gone() else visible()
}

fun String.containsTurkishCharacters(): Boolean {
    val turkishCharacters = listOf('ç', 'ğ', 'ı', 'ö', 'ş', 'ü', 'Ç', 'Ğ', 'İ', 'Ö', 'Ş', 'Ü')
    return any { turkishCharacters.contains(it) }
}
