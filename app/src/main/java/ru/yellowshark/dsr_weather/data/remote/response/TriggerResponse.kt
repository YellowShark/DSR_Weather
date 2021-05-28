package ru.yellowshark.dsr_weather.data.remote.response


import com.google.gson.annotations.SerializedName

data class TriggerResponse(
    @SerializedName("alerts")
    val alerts: Any,
    @SerializedName("area")
    val area: List<Area>,
    @SerializedName("conditions")
    val conditions: List<Condition>,
    @SerializedName("_id")
    val id: String,
    @SerializedName("time_period")
    val timePeriod: TimePeriod,
    @SerializedName("__v")
    val v: Int
)

data class Area(
    @SerializedName("coordinates")
    val coordinates: List<Int>,
    @SerializedName("_id")
    val id: String,
    @SerializedName("type")
    val type: String
)

data class Condition(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("expression")
    val expression: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String
)

data class End(
    @SerializedName("amount")
    val amount: Long,
    @SerializedName("expression")
    val expression: String
)

data class Start(
    @SerializedName("amount")
    val amount: Long,
    @SerializedName("expression")
    val expression: String
)

data class TimePeriod(
    @SerializedName("end")
    val end: End,
    @SerializedName("start")
    val start: Start
)