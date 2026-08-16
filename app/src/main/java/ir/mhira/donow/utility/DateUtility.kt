package ir.mhira.donow.utility

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun longToDateShortForm(timestamp: Long): String {
    val formatter = SimpleDateFormat("MMM dd", Locale.getDefault())
    return formatter.format(Date(timestamp))
}

fun longToDateLongForm(timestamp: Long): String {
    val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    return formatter.format(Date(timestamp))
}

fun getTomorrowLang(): Long {
    return System.currentTimeMillis() + 24 * 60 * 60 * 1000
}

fun getTodayLong(): Long{
    return System.currentTimeMillis()
}
