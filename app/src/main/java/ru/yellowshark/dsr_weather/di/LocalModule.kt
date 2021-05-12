package ru.yellowshark.dsr_weather.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.yellowshark.dsr_weather.data.db.LocationsDatabase
import ru.yellowshark.dsr_weather.data.db.dao.LocationsDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    fun provideLocationsDao(db: LocationsDatabase): LocationsDao {
        return db.getLocationsDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): LocationsDatabase {
        return LocationsDatabase(context)
    }
}