package ru.yellowshark.dsr_weather.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ConnectivityInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiKeyInterceptor