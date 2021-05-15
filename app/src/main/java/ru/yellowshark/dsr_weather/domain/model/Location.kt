package ru.yellowshark.dsr_weather.domain.model

data class Location(
    var id: Int,
    var temp: Int,
    var city: String,
    var lat: Long = 0L,
    var lon: Long = 0L,
    var hasNextDayForecast: Boolean = false,
    var isFavorite: Boolean = false,
)