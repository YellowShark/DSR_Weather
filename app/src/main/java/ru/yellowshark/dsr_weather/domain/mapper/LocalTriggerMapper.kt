package ru.yellowshark.dsr_weather.domain.mapper

import ru.yellowshark.dsr_weather.data.db.entity.TriggerEntity
import ru.yellowshark.dsr_weather.domain.model.Trigger
import ru.yellowshark.dsr_weather.utils.DateConverter
import ru.yellowshark.dsr_weather.utils.Mapper

class LocalTriggerMapper : Mapper<Trigger, TriggerEntity> {
    override fun toDomain(dto: TriggerEntity): Trigger {
        with(dto) {
            return Trigger(
                id,
                name,
                temp,
                humidity,
                wind,
                DateConverter.dateFormat(startMillis),
                DateConverter.dateFormat(endMillis)
            )
        }
    }

    override fun fromDomain(domain: Trigger): TriggerEntity {
        with(domain) {
            return TriggerEntity(
                id,
                name,
                temp,
                humidity,
                wind,
                DateConverter.parseString(startDate),
                DateConverter.parseString(endDate)
            )
        }
    }
}