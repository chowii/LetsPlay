package au.com.letspay.letspayapp.util

import au.com.letspay.letspayapp.LetsPayApplication
import au.com.letspay.letspayapp.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by chowii on 2/4/18.
 */
fun Calendar.getCalendar(date: String?, pattern: String = "dd/MM/yyyy") {
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    this.time = format.parse(date)
}

fun Date.durationFromNow(): Date {
    val durationLong = System.currentTimeMillis() - this.time
    return Date(durationLong)
}

fun Date.durationFromNowAsString(): String {
    val adjustIndex = 1
    val duration = this.durationFromNow()
    Calendar.getInstance().let {
        it.time = duration
        return getDuration(it.timeInMillis)

    }
}

private fun getDuration(timeInMillis: Long): String {
    val resources = LetsPayApplication.context.resources
    val milliseconds = TimeUnit.MILLISECONDS
    val seconds = milliseconds.toSeconds(timeInMillis)
    val minutes = milliseconds.toMinutes(timeInMillis)
    val hours = milliseconds.toHours(timeInMillis)
    val days = milliseconds.toDays(timeInMillis)

    return if (seconds < 60)
        "Just now"
    else if (minutes < 60)
        resources.getQuantityString(R.plurals.minute, minutes.toInt(), minutes)
    else if (hours < 24)
        resources.getQuantityString(R.plurals.hour, hours.toInt(), hours)
    else if (days < 30)
        resources.getQuantityString(R.plurals.day, days.toInt(), days)
    else {
        val month = days / 30
        if (month < 12)
            resources.getQuantityString(R.plurals.month, month.toInt(), month)
        else {
            val year = month / 12
            resources.getQuantityString(R.plurals.year, year.toInt(), year)
        }
    }
}