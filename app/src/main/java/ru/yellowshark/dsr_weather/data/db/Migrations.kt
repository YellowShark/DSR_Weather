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