package ru.yellowshark.dsr_weather.domain.model

data class Trigger(
    var id: Int,
    val name: String,
    val locationName: String,
    val lat: Double,
    val lon: Double,
    val temp: Int,
    val humidity: Int? = null,
    val wind: Int? = null,
    val startDate: String = "",
    val endDate: String = ""
)