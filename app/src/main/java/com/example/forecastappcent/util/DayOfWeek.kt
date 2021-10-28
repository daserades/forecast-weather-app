package com.example.forecastappcent.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DayOfWeek {
    companion object {
        /**
         * Verilen tarihe göre hangi gün oldugunu bulur ...
         */
        fun findDayOfWeek(dateStr: String): String {
            return try {
                var dayOfWeek = ""
                val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val date = df.parse(dateStr) // bulmak istediğimiz tarih  yıl ay gün şeklinde
                val calendar = Calendar.getInstance()
                calendar.time = date
                val day = calendar[Calendar.DAY_OF_WEEK] - 1
                when (day) {
                    0 -> dayOfWeek = "Sunday"
                    1 -> dayOfWeek = "Monday"
                    2 -> dayOfWeek = "Tuesday"
                    3 -> dayOfWeek = "Wednesday"
                    4 -> dayOfWeek = "Thursday"
                    5 -> dayOfWeek = "Friday"
                    6 -> dayOfWeek = "Saturday"
                    7 -> dayOfWeek = "Sunday"
                }
                dayOfWeek
            } catch (e: Exception) {
                ""
            }
        }
    }
}