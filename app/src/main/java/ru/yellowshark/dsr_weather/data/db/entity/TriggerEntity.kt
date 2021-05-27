package ru.yellowshark.dsr_weather.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trigger_entity")
data class TriggerEntity(
    @PrimaryKey
    var id: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "temp")
    var temp: Int?,
    @ColumnInfo(name = "humidity")
    var humidity: Int?,
    @ColumnInfo(name = "wind")
    var wind: Int?,
    @ColumnInfo(name = "start_millis")
    var startMillis: Long,
    @ColumnInfo(name = "end_millis")
    var endMillis: Long,
)
