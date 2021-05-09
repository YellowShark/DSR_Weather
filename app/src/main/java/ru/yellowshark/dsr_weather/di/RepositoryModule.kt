package ru.yellowshark.dsr_weather.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.yellowshark.dsr_weather.data.RepositoryImpl
import ru.yellowshark.dsr_weather.data.remote.api.ForecastApi
import ru.yellowshark.dsr_weather.domain.mapper.NetworkForecastMapper
import ru.yellowshark.dsr_weather.domain.repository.Repository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(
        api: ForecastApi,
        networkMapper: NetworkForecastMapper
    ): Repository {
        return RepositoryImpl(api, networkMapper)
    }

    @Provides
    fun provideNetworkForecastMapper() = NetworkForecastMapper()
}