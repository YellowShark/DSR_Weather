package ru.yellowshark.dsr_weather.domain.mapper

import android.util.Log
import ru.yellowshark.dsr_weather.data.remote.response.TriggerResponse
import ru.yellowshark.dsr_weather.domain.model.Trigger
import ru.yellowshark.dsr_weather.utils.Mapper

class TriggerMapper : Mapper<Trigger, TriggerResponse> {
    override fun toDomain(dto: TriggerResponse): Trigger {
        Log.d("TAGGG", "toDomain: ${dto.alerts}")
        return Trigger(dto.id, dto.id, 0)
    }

    override fun fromDomain(domain: Trigger): TriggerResponse {
        TODO("Not yet implemented")
    }
}