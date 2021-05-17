package ru.yellowshark.dsr_weather.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "temp")
    var temp: String,
    @ColumnInfo(name = "future_temp")
    var futureTemp: String,
    @ColumnInfo(name = "city")
    var city: String,
    @ColumnInfo(name = "lat")
    var lat: Long = 0L,
    @ColumnInfo(name = "lon")
    var lon: Long = 0L,
    @ColumnInfo(name = "has_next_day_forecast")
    var hasNextDayForecast: Boolean = false,
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false,
)