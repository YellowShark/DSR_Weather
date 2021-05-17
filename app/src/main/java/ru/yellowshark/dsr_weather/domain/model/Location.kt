package ru.yellowshark.dsr_weather.domain.model

data class Location(
    var id: Int,
    var temp: String,
    var city: String,
    var futureTemp: String = "",
    var lat: Double = 0.0,
    var lon: Double = 0.0,
    var hasNextDayForecast: Boolean = false,
    var isFavorite: Boolean = false,
)