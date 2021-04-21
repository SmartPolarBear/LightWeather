package com.smartpolarbear.lightweather.weather

import com.smartpolarbear.lightweather.settings.DisplayUnit
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QWeatherRetriever {
    private val service: QWeatherService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return@lazy retrofit.create(QWeatherService::class.java)
    }

    companion object {
        const val BASE_URL = "https://devapi.qweather.com/"
    }


    suspend fun getNowWeather(
        location: Location,
        lang: DisplayLanguages,
        unit: DisplayUnit,
        comp: Compression
    ): Response<NowWeatherResponse> {
        return service.getNowWeatherAsync(location, lang, unit, comp)
    }
}