package ru.akumakeito.githubusers.presentation

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class Utils {

    companion object {
        fun convertStringToDateStrin(date : String) : String {
            val instant = Instant.parse(date)
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            return instant.atZone(ZoneId.systemDefault()).format(formatter)
        }
    }
}