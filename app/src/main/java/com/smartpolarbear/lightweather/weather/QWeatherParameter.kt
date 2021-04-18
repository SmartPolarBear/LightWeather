package com.smartpolarbear.lightweather.weather

enum class LocationType {
    LOCATION_ID,
    COORDINATE
}

enum class DisplayLanguages(val langCode: String) {
    ZH(langCode = "zh"),
    EN(langCode = "en");

    override fun toString(): String {
        return langCode
    }
}

enum class Compression(val param: String) {
    GZIP(param = "y"),
    DISABLED(param = "n")
}

data class Location(val location: String, val type: LocationType) {
    override fun toString(): String {
        return location
    }
}