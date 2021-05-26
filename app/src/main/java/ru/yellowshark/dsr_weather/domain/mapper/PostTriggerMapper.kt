package ru.yellowshark.dsr_weather.domain.mapper

import ru.yellowshark.dsr_weather.data.remote.request.*
import ru.yellowshark.dsr_weather.domain.model.Trigger
import ru.yellowshark.dsr_weather.utils.Mapper

class PostTriggerMapper : Mapper<Trigger, AddTriggerRequest> {
    override fun toDomain(dto: AddTriggerRequest): Trigger {
        TODO("Not yet implemented")
    }

    override fun fromDomain(domain: Trigger): AddTriggerRequest {
        return AddTriggerRequest(
            area = listOf(
                Area(
                    listOf(0, 1),
                    ""
                )
            ),
            conditions = listOf(
                Condition(
                    100,
                    "\$gte",
                    "temp"
                )
            ),
            TimePeriod(
                start = Start(0,""),
                end = End(0, "")
            )
        )
    }
}