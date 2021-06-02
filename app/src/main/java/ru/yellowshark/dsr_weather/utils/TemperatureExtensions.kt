package ru.yellowshark.dsr_weather.utils


fun Int.celsiusToKelvin(): Int {
    return this + 273
}

fun Int.celsiusFromKelvin(): Int {
    return this - 273
}

fun Int.fahrenheitToKelvin(): Int {
    return (this - 32) * (5 / 9) + 273
}

fun Int.fahrenheitFromKelvin(): Int {
    return (this - 273) * (9 / 5) + 32
}