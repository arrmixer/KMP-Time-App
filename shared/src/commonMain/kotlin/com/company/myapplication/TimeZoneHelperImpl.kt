package com.company.myapplication

import io.github.aakira.napier.Napier
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class TimeZoneHelperImpl : TimeZoneHelper {

    companion object {
        private const val AM_SUFFIX = " am"
        private const val PM_SUFFIX = " pm"
        private const val COLON_STRING = ":"
        private const val ZERO_STRING = "0"
    }

    override fun getTimeZoneStrings(): List<String> {
        return TimeZone.availableZoneIds.sorted()
    }

    override fun currentTime(): String {
        // get the current instant in the UTC time zone
        val currentMoment: Instant = Clock.System.now()

        // Convert the current moment into a LocalDateTime that’s
        // based on the current user’s time zone.
        val dateTime: LocalDateTime = currentMoment
            .toLocalDateTime(TimeZone.currentSystemDefault())

        // use method to get the date string
        return formatDateTime(dateTime)
    }

    override fun currentTimeZone(): String {
        val currentTimeZone = TimeZone.currentSystemDefault()
        return currentTimeZone.toString()
    }

    override fun hoursFromTimeZone(otherTimeZoneId: String): Double {
        // Get the current time zone
        val currentTimeZone = TimeZone.currentSystemDefault()

        // get the current instant in the UTC time zone
        val currentUTCInstant: Instant = Clock.System.now()

        // Date time in other timezone
        // Get the other time zone
        val otherTimeZone = TimeZone.of(otherTimeZoneId)

        // Convert the current time into a LocalDateTime class
        val currentDateTime: LocalDateTime = currentUTCInstant.toLocalDateTime(currentTimeZone)

        // Convert the current time in another time zone into a LocalDateTime class
        val currentOtherDateTime: LocalDateTime = currentUTCInstant.toLocalDateTime(otherTimeZone)

        // Return the absolute difference between the hours (should not be negative),
        // making sure the result is a double
        return abs((currentDateTime.hour - currentOtherDateTime.hour) * 1.0)
    }

    override fun getTime(timezoneId: String): String {
        // get time zone base on timeZone Id
        val timezone: TimeZone = TimeZone.of(timezoneId)

        // get the current instant in the UTC time zone
        val currentMoment: Instant = Clock.System.now()

        // Convert the current moment into a LocalDateTime
        // that’s based on the passed-in time zone.
        val dateTime: LocalDateTime = currentMoment.toLocalDateTime(timezone)

        // use method to get the date string
        return formatDateTime(dateTime)
    }

    override fun getDate(timezoneId: String): String {
        val timezone = TimeZone.of(timezoneId)
        val currentMoment: Instant = Clock.System.now()
        val dateTime: LocalDateTime = currentMoment.toLocalDateTime(timezone)

        // get day of the week, month, and day of the month based on the dateTime object
        val dayOfWeek = dateTime.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
        val month = dateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }
        val dayOfMonth = dateTime.date.dayOfMonth

        // create Date string ex. Monday, October 4
        return "$dayOfWeek, $month $dayOfMonth"
    }

    override fun search(startHour: Int, endHour: Int, timezoneStrings: List<String>): List<Int> {
        // Create a list to return all the hours that are valid
        val goodHours = mutableListOf<Int>()

        // Create a time range from start to end hours
        val timeRange = IntRange(max(0, startHour), min(23, endHour))

        // Get the current time zone
        val currentTimeZone = TimeZone.currentSystemDefault()

        // Go through each hour in the time range
        for (hour in timeRange) {
            var isGoodHour = false
            // Go through each time zone in the time zone list
            for (zone in timezoneStrings) {
                val timezone = TimeZone.of(zone)
                // If it’s the same time zone as the current one, then you know it’s good
                if (timezone == currentTimeZone) {
                    continue
                }
                // Check if the hour is valid
                if (!isValid(
                        timeRange = timeRange,
                        hour = hour,
                        currentTimeZone = currentTimeZone,
                        otherTimeZone = timezone
                    )
                ) {
                    Napier.d("Hour $hour is not valid for time range")
                    isGoodHour = false
                    break
                } else {
                    Napier.d("Hour $hour is Valid for time range")
                    isGoodHour = true
                }
            }
            // If, after going through every hour and it’s a good hour, add it to our list
            if (isGoodHour) {
                goodHours.add(hour)
            }
        }
        // Return the list of good hours
        return goodHours
    }

    /**
     * This method takes a [IntRange] (like 8..17), the given hour to check,
     * the current time zone for the user and the other time zone that you’re
     * checking against. The first check verifies if the hour is in the time range.
     * If not, it isn’t valid.
     */
    private fun isValid(
        timeRange: IntRange,
        hour: Int,
        currentTimeZone: TimeZone,
        otherTimeZone: TimeZone
    ): Boolean {
        // check in hour is in range
        if (hour !in timeRange) {
            return false
        }

        // get the current instant in the UTC time zone
        val currentUTCInstant: Instant = Clock.System.now()

        // Convert the instant into another time zone with toLocalDateTim
        val currentOtherDateTime: LocalDateTime = currentUTCInstant
            .toLocalDateTime(otherTimeZone)

        // Get a LocalDateTime with the given hour ignore Minutes, seconds and nanoseconds
        val otherDateTimeWithHour = LocalDateTime(
            currentOtherDateTime.year,
            currentOtherDateTime.monthNumber,
            currentOtherDateTime.dayOfMonth,
            hour,
            0,
            0,
            0
        )

        // Convert that hour into the current time zone
        val localInstant = otherDateTimeWithHour.toInstant(currentTimeZone)

        // Convert your time zone hour to the other time zone
        val convertedTime = localInstant.toLocalDateTime(otherTimeZone)
        // add log
        Napier.d("Hour $hour in Time Range ${otherTimeZone.id} is ${convertedTime.hour}")

        // Check to see if it’s in your time range
        return convertedTime.hour in timeRange
    }


    /**
     * Format the [LocalDateTime] into a String.
     * format in 12hr format.
     * ex. 1:09 pm or 08:09 am
     */
    private fun formatDateTime(dateTime: LocalDateTime): String {
        val stringBuilder = StringBuilder()
        // Get the hour and minutes from the dateTime argument
        val minute = dateTime.minute
        var hour = dateTime.hour % 12
        if (hour == 0) hour = 12
        // For 12hr clock, check if the hour is greater than noon (12)
        val amPm = if (dateTime.hour < 12) AM_SUFFIX else PM_SUFFIX
        // Build the hour and colon
        stringBuilder.append(hour.toString())
        stringBuilder.append(COLON_STRING)
        // make sure numbers 0-9 are padded with a zero string
        if (minute < 10) {
            stringBuilder.append(ZERO_STRING)
        }
        stringBuilder.append(minute.toString())
        stringBuilder.append(amPm)

        return stringBuilder.toString()
    }
}