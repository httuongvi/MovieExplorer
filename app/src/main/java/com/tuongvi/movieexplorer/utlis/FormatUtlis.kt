package com.tuongvi.movieexplorer.utlis

import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FormatUtlis {
    fun formatRating(rating: Double, locale: Locale = Locale.getDefault()): String{
        val formatter = NumberFormat.getInstance(locale).apply {
            maximumFractionDigits = 1
            minimumIntegerDigits = 1
        }
        return formatter.format(rating)
    }

    fun formatReleaseDate(rawDate: String, locale: Locale = Locale.getDefault()): String{
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date: Date? = inputFormat.parse(rawDate)

            if (date != null){
                val formatter = DateFormat.getDateInstance(DateFormat.MEDIUM, locale)
                formatter.format(date)
            } else rawDate
        } catch (e: Exception){
            rawDate
        }
    }
}