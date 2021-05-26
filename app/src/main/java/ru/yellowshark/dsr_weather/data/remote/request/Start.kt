package ru.yellowshark.dsr_weather.data.remote.request


import com.google.gson.annotations.SerializedName

data class Start(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("expression")
    val expression: String
)