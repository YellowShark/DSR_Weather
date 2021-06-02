package ru.yellowshark.dsr_weather.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal val MIGRATION_10_11 = object : Migration(10, 11) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE triggers ADD COLUMN lat REAL DEFAULT 0 NOT NULL")
        db.execSQL("ALTER TABLE triggers ADD COLUMN lon REAL DEFAULT 0 NOT NULL")
    }
}

internal val MIGRATION_11_12 = object : Migration(11, 12) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE triggers ADD COLUMN location_name TEXT DEFAULT 0 NOT NULL")
    }
}

internal val MIGRATION_12_13 = object : Migration(12, 13) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS `triggers_temp` ( `id` INTEGER NOT NULL," +
                    " `name` TEXT NOT NULL," +
                    " `location_name` TEXT NOT NULL, `lat` REAL NOT NULL, `lon` REAL NOT NULL," +
                    " `temp` INTEGER NOT NULL, `humidity` INTEGER, `wind` INTEGER," +
                    " `start_millis` INTEGER NOT NULL, `end_millis` INTEGER NOT NULL, PRIMARY KEY(`id`))"
        )
        db.execSQL("INSERT INTO triggers_temp(" +
                "name, location_name, lat, lon, `temp`, humidity, wind, start_millis, end_millis)" +
                " SELECT name, location_name, lat, lon, `temp`, humidity, wind, start_millis, end_millis" +
                " FROM triggers")
        db.execSQL("DROP TABLE triggers")
        db.execSQL("ALTER TABLE triggers_temp RENAME TO triggers")
    }

}