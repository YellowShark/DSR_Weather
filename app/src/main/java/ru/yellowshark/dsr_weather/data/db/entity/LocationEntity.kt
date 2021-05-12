package ru.yellowshark.dsr_weather.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "lat")
    val lat: Long = 0L,
    @ColumnInfo(name = "lon")
    val lon: Long = 0L,
    @ColumnInfo(name = "has_next_day_forecast")
    val hasNextDayForecast: Boolean = false,
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false,
)