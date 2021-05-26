package ru.yellowshark.dsr_weather.data.remote.request


import com.google.gson.annotations.SerializedName

data class Area(
    @SerializedName("coordinates")
    val coordinates: List<Int>,
    @SerializedName("type")
    val type: String
)