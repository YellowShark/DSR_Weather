package ru.yellowshark.dsr_weather.utils

interface Mapper<Domain, Dto> {
    fun toDomain(dto: Dto): Domain
    fun fromDomain(domain: Domain): Dto
}