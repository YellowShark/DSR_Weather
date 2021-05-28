package ru.yellowshark.dsr_weather.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    companion object {
        const val DATE_FORMAT = "dd/MM/yyyy"

        fun parseString(dateFormatted: String): Long {
            val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            var date: Date? = null
            try {
                date = sdf.parse(dateFormatted)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            var millis: Long = 0
            if (date != null) {
                millis = date.time
            }
            return millis
        }

        fun dateFormat(millis: Long): String {
            if (millis == 0L) return ""
            val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            val date = Date(millis)
            return formatter.format(date)
        }
    }
}