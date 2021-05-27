package ru.yellowshark.dsr_weather.domain.model

data class Trigger(
    val id: String,
    val name: String,
    val temp: Int? = null,
    val humidity: Int? = null,
    val wind: Int? = null,
    val startDate: String = "",
    val endDate: String = "",
    val areas: List<Point> = emptyList()
)