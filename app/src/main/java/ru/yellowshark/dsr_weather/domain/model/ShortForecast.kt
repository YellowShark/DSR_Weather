package ru.yellowshark.dsr_weather.domain.model

data class ShortForecast(
    val time: String,
    val icon: String,
    val temp: String,
    val humidity: String
)
