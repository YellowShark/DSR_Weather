package ru.yellowshark.dsr_weather.data.remote.api

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*
import ru.yellowshark.dsr_weather.data.remote.request.AddTriggerRequest
import ru.yellowshark.dsr_weather.data.remote.response.TriggerResponse

interface TriggersApi {
    @POST("data/3.0/triggers")
    fun saveTrigger(@Body trigger: AddTriggerRequest): Single<TriggerResponse>

    @GET("data/3.0/triggers")
    fun getTriggers(): Observable<List<TriggerResponse>>

    @DELETE("data/3.0/triggers/{id}")
    fun deleteTrigger(@Path("id") triggerId: String): Completable
}