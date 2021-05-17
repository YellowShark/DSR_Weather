package ru.yellowshark.dsr_weather.domain.model

data class Forecast(
    val id: Int,
    val cityName: String,
    val date: String,
    val temperature: String,
    val humidity: String,
    val icon: String,
    val description: String,
    val pressure: String,
    val wind: String,
)