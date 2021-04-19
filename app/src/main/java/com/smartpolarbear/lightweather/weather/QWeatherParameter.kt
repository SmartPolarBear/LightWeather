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
    companion object {
        fun fromLocationId(locationId: Int): Location {
            return Location(locationId.toString(), LocationType.LOCATION_ID)
        }

        fun fromCoordinate(x: Double, y: Double): Location {
            return Location(
                "${String.format("%.2f", x)},${String.format("%.2f", y)}",
                LocationType.COORDINATE
            )
        }
    }

    override fun toString(): String {
        return location
    }
}