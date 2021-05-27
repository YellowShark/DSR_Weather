package ru.yellowshark.dsr_weather.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.yellowshark.dsr_weather.data.db.LocationsDatabase
import ru.yellowshark.dsr_weather.data.db.dao.LocationsDao
import ru.yellowshark.dsr_weather.data.db.dao.TriggersDao
import ru.yellowshark.dsr_weather.data.other.UnitManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    fun provideLocationsDao(db: LocationsDatabase): LocationsDao {
        return db.getLocationsDao()
    }

    @Provides
    fun provideTriggersDao(db: LocationsDatabase): TriggersDao =
        db.getTriggersDao()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): LocationsDatabase {
        return LocationsDatabase(context)
    }

    @Provides
    fun provideMeasureSystemManager(@ApplicationContext context: Context): UnitManager {
        return UnitManager(context)
    }
}