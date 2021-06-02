package ru.yellowshark.dsr_weather.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.yellowshark.dsr_weather.data.db.dao.LocationsDao
import ru.yellowshark.dsr_weather.data.db.dao.TriggersDao
import ru.yellowshark.dsr_weather.data.db.entity.LocationEntity
import ru.yellowshark.dsr_weather.data.db.entity.TriggerEntity

const val DB_NAME = "locations.db"

@Database(entities = [LocationEntity::class, TriggerEntity::class], version = 13)
abstract class LocationsDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: LocationsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                LocationsDatabase::class.java,
                DB_NAME
            )
                .addMigrations(MIGRATION_10_11, MIGRATION_11_12, MIGRATION_12_13)
                .build()
    }

    abstract fun getLocationsDao(): LocationsDao

    abstract fun getTriggersDao(): TriggersDao
}