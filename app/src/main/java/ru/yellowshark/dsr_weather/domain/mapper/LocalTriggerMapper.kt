package ru.yellowshark.dsr_weather.domain.mapper

import ru.yellowshark.dsr_weather.data.db.entity.TriggerEntity
import ru.yellowshark.dsr_weather.domain.model.Trigger
import ru.yellowshark.dsr_weather.utils.DateConverter
import ru.yellowshark.dsr_weather.utils.Mapper
import java.util.*

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
            var startMillis: Long =
                if (startDate.isEmpty()) Calendar.getInstance().timeInMillis else DateConverter.parseString(startDate)
            if (startMillis < 0)
                startMillis = 0
            val endMillis = if (endDate.isEmpty()) startMillis + 24 * 60 * 60 * 1000 else DateConverter.parseString(endDate)

            return TriggerEntity(
                id,
                name,
                temp,
                humidity,
                wind,
                startMillis,
                endMillis
            )
        }
    }
}