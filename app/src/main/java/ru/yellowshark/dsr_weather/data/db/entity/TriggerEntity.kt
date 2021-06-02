package ru.yellowshark.dsr_weather.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "triggers")
data class TriggerEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "location_name")
    var locationName: String,
    @ColumnInfo(name = "lat")
    var lat: Double,
    @ColumnInfo(name = "lon")
    var lon: Double,
    @ColumnInfo(name = "temp")
    var temp: Int,
    @ColumnInfo(name = "humidity")
    var humidity: Int?,
    @ColumnInfo(name = "wind")
    var wind: Int?,
    @ColumnInfo(name = "start_millis")
    var startMillis: Long,
    @ColumnInfo(name = "end_millis")
    var endMillis: Long,
)
