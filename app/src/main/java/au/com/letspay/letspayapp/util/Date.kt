package au.com.letspay.letspayapp.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by chowii on 2/4/18.
 */
fun Calendar.getCalendar(date: String?, pattern: String = "dd/MM/yyyy") {
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    this.time = format.parse(date)
}