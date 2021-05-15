package ru.yellowshark.dsr_weather.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import ru.yellowshark.dsr_weather.data.db.entity.LocationEntity

@Dao
interface LocationsDao {
    @Insert
    fun insertLocation(locationEntity: LocationEntity): Completable

    @Query("SELECT * FROM locations")
    fun getLocations(): Single<List<LocationEntity>>

    @Query("""
        UPDATE locations
        SET `temp` = :newTemp
        WHERE id = :locationId
        """)
    fun updateLocationTemp(locationId: Int, newTemp: Int): Completable
}