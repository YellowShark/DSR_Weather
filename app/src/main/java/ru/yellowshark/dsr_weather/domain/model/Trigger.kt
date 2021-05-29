package ru.yellowshark.dsr_weather.domain.model

data class Trigger(
    var id: String,
    val name: String,
    val temp: Int,
    val humidity: Int? = null,
    val wind: Int? = null,
    val startDate: String = "",
    val endDate: String = "",
    var areas: List<Point> = emptyList()
)