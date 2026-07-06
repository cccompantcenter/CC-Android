package com.cc.commandcenter.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val inputFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd")

private val outputFormatter =
    DateTimeFormatter.ofPattern("dd-MM-yyyy")

fun formatDueDate(date: String?): String {

    if (date.isNullOrBlank()) {
        return ""
    }

    return try {
        LocalDate.parse(date, inputFormatter)
            .format(outputFormatter)
    } catch (e: Exception) {
        date
    }
}