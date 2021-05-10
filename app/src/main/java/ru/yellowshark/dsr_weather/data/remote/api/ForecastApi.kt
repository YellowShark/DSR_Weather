package ru.yellowshark.dsr_weather.data.remote.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.yellowshark.dsr_weather.data.remote.response.AllDayForecastResponse
import ru.yellowshark.dsr_weather.data.remote.response.ForecastResponse

interface ForecastApi {
    @GET("data/2.5/weather")
    fun getForecast(@Query("q") city: String): Single<ForecastResponse>

    @GET("data/2.5/forecast")
    fun getAllDayForecast(
        @Query("q") city: String,
        @Query("cnt") cnt: Int = 8
    ): Single<AllDayForecastResponse>
}