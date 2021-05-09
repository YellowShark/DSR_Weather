package ru.yellowshark.dsr_weather.domain.model

data class Forecast(
    val temperature: String,
    val humidity: String,
    val icon: String,
    val description: String,
    val pressure: String,
    val windSpeed: String,
    val windDirection: String
)