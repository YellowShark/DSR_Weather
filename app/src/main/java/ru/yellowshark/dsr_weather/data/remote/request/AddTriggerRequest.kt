package ru.yellowshark.dsr_weather.data.remote.request


import com.google.gson.annotations.SerializedName

data class AddTriggerRequest(
    @SerializedName("area")
    val area: List<Area>,
    @SerializedName("conditions")
    val conditions: List<Condition>,
    @SerializedName("time_period")
    val timePeriod: TimePeriod
)