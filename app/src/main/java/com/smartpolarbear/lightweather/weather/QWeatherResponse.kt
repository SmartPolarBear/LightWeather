package com.smartpolarbear.lightweather.weather

import com.google.gson.annotations.SerializedName


data class NowWeatherResponse(
    @SerializedName("code")
    val code: String,
    @SerializedName("fxLink")
    val fxLink: String,
    @SerializedName("now")
    val now: Now,
    @SerializedName("refer")
    val refer: Refer,
    @SerializedName("updateTime")
    val updateTime: String
)

data class Now(
    @SerializedName("cloud")
    val cloud: String,
    @SerializedName("dew")
    val dew: String,
    @SerializedName("feelsLike")
    val feelsLike: String,
    @SerializedName("humidity")
    val humidity: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("obsTime")
    val obsTime: String,
    @SerializedName("precip")
    val precip: String,
    @SerializedName("pressure")
    val pressure: String,
    @SerializedName("temp")
    val temp: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("vis")
    val vis: String,
    @SerializedName("wind360")
    val wind360: String,
    @SerializedName("windDir")
    val windDir: String,
    @SerializedName("windScale")
    val windScale: String,
    @SerializedName("windSpeed")
    val windSpeed: String
)

data class Refer(
    @SerializedName("license")
    val license: List<String>,
    @SerializedName("sources")
    val sources: List<String>
)