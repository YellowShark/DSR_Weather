package ru.yellowshark.dsr_weather.data.remote.request


import com.google.gson.annotations.SerializedName

data class TimePeriod(
    @SerializedName("end")
    val end: End,
    @SerializedName("start")
    val start: Start
)