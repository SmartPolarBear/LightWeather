package com.smartpolarbear.lightweather

import com.smartpolarbear.lightweather.settings.DisplayUnit
import com.smartpolarbear.lightweather.weather.Compression
import com.smartpolarbear.lightweather.weather.DisplayLanguages
import com.smartpolarbear.lightweather.weather.Location
import com.smartpolarbear.lightweather.weather.QWeatherRetriever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test


class QWeatherRetrieverTest {

    @ExperimentalCoroutinesApi
    @Test
    fun testGetNowWeather() =  runBlocking {
        val retriever = QWeatherRetriever()
        val nowWeather = retriever.getNowWeather(
            location = Location.fromLocationId(101010100),
            lang = DisplayLanguages.ZH,
            unit = DisplayUnit.METRIC,
            comp = Compression.GZIP
        )
    }
}