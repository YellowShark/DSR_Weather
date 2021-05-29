package ru.yellowshark.dsr_weather.data.remote.response


import com.google.gson.annotations.SerializedName
import java.util.*

data class Alerts(
    val alertData: TreeMap<String, AlertData?>?
) {

    data class Condition(
        @SerializedName("condition")
        val condition: ConditionX,
        @SerializedName("current_value")
        val currentValue: CurrentValue
    )

    data class ConditionX(
        @SerializedName("amount")
        val amount: Int,
        @SerializedName("expression")
        val expression: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("name")
        val name: String
    )

    data class Coordinates(
        @SerializedName("lat")
        val lat: Int,
        @SerializedName("lon")
        val lon: Int
    )

    data class CurrentValue(
        @SerializedName("max")
        val max: Double,
        @SerializedName("min")
        val min: Double
    )

    data class AlertData(
        @SerializedName("conditions")
        val conditions: List<ru.yellowshark.dsr_weather.data.remote.response.Condition>,
        @SerializedName("coordinates")
        val coordinates: Coordinates,
        @SerializedName("date")
        val date: Long,
        @SerializedName("last_update")
        val lastUpdate: Long
    )
}