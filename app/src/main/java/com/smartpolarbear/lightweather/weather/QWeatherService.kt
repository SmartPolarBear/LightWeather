package com.smartpolarbear.lightweather.weather

import com.smartpolarbear.lightweather.settings.DisplayUnit
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QWeatherService {

    companion object {
        const val PUBLIC_ID = "HE2104041152321459"
        const val API_KEY = "9c53b8293d174c06a5292082cc4ab68d"
    }

    @GET("weather/now?key=${API_KEY}")
    suspend fun getNowWeatherAsync(
        @Query("location", encoded = true) location: Location,
        @Query("lang", encoded = true) lang: DisplayLanguages,
        @Query("unit", encoded = true) unit: DisplayUnit,
        @Query("gzip", encoded = true) comp: Compression
    ): Deferred<Response<NowWeatherResponse>>
}