package com.smartpolarbear.lightweather.weather

import com.smartpolarbear.lightweather.settings.DisplayUnit
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QWeatherRetriever {
    private val service: QWeatherService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        return@lazy retrofit.create(QWeatherService::class.java)
    }

    companion object {
        private const val API_VAR = "v7"

        const val BASE_URL = "https://devapi.qweather.com/${API_VAR}/"
    }


    suspend fun getNowWeather(
        location: Location,
        lang: DisplayLanguages,
        unit: DisplayUnit,
        comp: Compression
    ): Response<NowWeatherResponse> {
        return service.getNowWeatherAsync(location, lang, unit, comp).await()
    }
}