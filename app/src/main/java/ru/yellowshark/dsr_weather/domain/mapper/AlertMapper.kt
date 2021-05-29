package ru.yellowshark.dsr_weather.domain.mapper

import ru.yellowshark.dsr_weather.data.remote.response.Alerts
import ru.yellowshark.dsr_weather.data.remote.response.TriggerResponse
import ru.yellowshark.dsr_weather.utils.Mapper

class AlertMapper : Mapper<Map<String, Alerts>, List<TriggerResponse>> {
    override fun toDomain(dto: List<TriggerResponse>): Map<String, Alerts> {
        val map = mutableMapOf<String, Alerts>()
        dto.forEach { map[it.id] = it.alerts }
        return map.toMap()
    }

    override fun fromDomain(domain: Map<String, Alerts>): List<TriggerResponse> {
        TODO("Not yet implemented")
    }
}