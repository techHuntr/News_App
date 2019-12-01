package com.ewind.newsapi.util.ext

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


const val YYYY_MM_DD = "yyyy-MM-dd"
const val DD_MMM_YYYY = "dd MMM, yyyy"
const val DD_MMM_YYYY_H_MM_A = "dd MMM, yyyy  h:mm a"
const val YYYY_MM_DDThh_mm_ssZ = "yyyy-MM-dd'T'HH:mm:ss'Z'" //2019-11-28T16:10:00Z



fun String.toDate(format: String): Date? {
    val simpleDateFormat = SimpleDateFormat(format, Locale.US)
    return try {
        simpleDateFormat.parse(this)
    } catch (e: ParseException) {
        null
    }
}

fun Date.toCustomDate(format: String): String {
    val simpleDateFormat = SimpleDateFormat(format, Locale.US)
    return simpleDateFormat.format(this)
}

fun String.toCustomDate(format: String): String {
    return this.toDate(YYYY_MM_DD)?.let {
        it.toCustomDate(format)
    } ?: ""
}


fun getDayOfMonthSuffix(n: Int): String {
    //checkArgument(n >= 1 && n <= 31, "illegal day of month: $n")
    if (n in 11..13) {
        return "th"
    }
    return when (n % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
}

/**
 * different with current time milis
 */
fun Long.differentFromCurrent(): Long {
    val dif = System.currentTimeMillis().minus(this)
    return dif
}


/**
 * get time title
 */
fun Long.getTime(): String {
    val dif = this.differentFromCurrent()
    val min = TimeUnit.MILLISECONDS.toMinutes(dif)
    val hours = TimeUnit.MINUTES.toHours(min)
    val days = TimeUnit.HOURS.toDays(hours)
    return if (min.toInt() == 0) {
        "Just now"
    } else if (min < 60) {
        "$min min ago"
    } else {
        if (hours < 24) {
            "${hours.toInt()}h ago"
        } else {
            if (days < 2) {
                "Yesterday"
            } else {
                val date = Date()
                date.time = this
                date.toCustomDate(DD_MMM_YYYY)
            }
        }
    }
}