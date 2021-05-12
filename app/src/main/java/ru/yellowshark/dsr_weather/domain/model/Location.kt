package ru.yellowshark.dsr_weather.domain.model

data class Location(
    val city: String,
    val temp: String,
    var isFavorite: Boolean = false
)