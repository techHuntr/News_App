package com.ewind.newsapi.util.ext

import android.app.DatePickerDialog
import java.util.*

fun setDatePickerListener(
    calendar: Calendar,
    func: Calendar.() -> Unit
): DatePickerDialog.OnDateSetListener =
    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        calendar.apply(func)
    }
