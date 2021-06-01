package ru.yellowshark.dsr_weather.data.remote.api

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.yellowshark.dsr_weather.data.remote.response.AllDayForecastResponse
import ru.yellowshark.dsr_weather.data.remote.response.ForecastResponse

interface ForecastApi {
    @GET("data/2.5/weather")
    fun getForecastByCityId(@Query("id") cityId: Int): Single<ForecastResponse>

    @GET("data/2.5/weather")
    fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Single<ForecastResponse>

    @GET("data/2.5/forecast")
    fun getAllDayForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("cnt") cnt: Int = 9
    ): Single<AllDayForecastResponse>

    @GET("data/2.5/forecast")
    fun getTwoDaysForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("cnt") cnt: Int = 13
    ): Observable<AllDayForecastResponse>
}