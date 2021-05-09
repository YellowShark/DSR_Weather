package ru.yellowshark.dsr_weather.data.remote.response


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)