package com.smartpolarbear.lightweather.weather

import com.smartpolarbear.lightweather.settings.DisplayUnit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class QWeatherRetriever {
    private val service: QWeatherService

    companion object {
        private const val API_VAR = "v7"

        const val BASE_URL = "https://devapi.qweather.com/${API_VAR}/"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(QWeatherService::class.java)
    }

    suspend fun getNowWeather(
        location: Location,
        lang: DisplayLanguages,
        unit: DisplayUnit,
        comp: Compression
    ): NowWeatherResponse {
        return service.getNowWeather(location, lang, unit, comp)
    }
}