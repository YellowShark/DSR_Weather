package ru.yellowshark.dsr_weather.data.remote.response


import com.google.gson.annotations.SerializedName

data class AllDayForecastResponse(
    @SerializedName("city")
    val city: City,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: List<Forecast>,
    @SerializedName("message")
    val message: Int
)

data class Forecast(
    @SerializedName("clouds")
    val clouds: Clouds2,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("dt_txt")
    val dtTxt: String,
    @SerializedName("main")
    val main: Main2,
    @SerializedName("pop")
    val pop: Double,
    @SerializedName("rain")
    val rain: Rain,
    @SerializedName("sys")
    val sys: Sys2,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("weather")
    val weather: List<Weather2>,
    @SerializedName("wind")
    val wind: Wind2
)

data class City(
    @SerializedName("coord")
    val coord: Coord2,
    @SerializedName("country")
    val country: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("population")
    val population: Int,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int,
    @SerializedName("timezone")
    val timezone: Int
)

data class Clouds2(
    @SerializedName("all")
    val all: Int
)

data class Coord2(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double
)

data class Main2(
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("grnd_level")
    val grndLevel: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("sea_level")
    val seaLevel: Int,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("temp_kf")
    val tempKf: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("temp_min")
    val tempMin: Double
)

data class Rain(
    @SerializedName("3h")
    val h: Double
)

data class Sys2(
    @SerializedName("pod")
    val pod: String
)

data class Weather2(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String
)

data class Wind2(
    @SerializedName("deg")
    val deg: Int,
    @SerializedName("gust")
    val gust: Double,
    @SerializedName("speed")
    val speed: Double
)