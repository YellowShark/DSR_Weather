package ru.yellowshark.dsr_weather.domain.model

data class Location(
    val city: String,
    val lat: Long = 0L,
    val lon: Long = 0L,
    val hasNextDayForecast: Boolean = false,
    var isFavorite: Boolean = false,
)