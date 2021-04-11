package com.smartpolarbear.lightweather.settings

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import com.smartpolarbear.lightweather.R

enum class DisplayUnit(val id: Int, @StringRes val displayName: Int) {
    METRIC(id = 1, displayName = R.string.metric),
    IMPERIAL(id = 1, displayName = R.string.imperial);

    fun save(context: Context) {
        val key = context.getString(R.string.display_unit_key)
        val sharedPref = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        with(sharedPref.edit())
        {
            putInt(key, id);
            apply()
        }
    }

    companion object {
        fun get(context: Context): DisplayUnit {
            val key = context.getString(R.string.display_unit_key)
            val sharedPref = context.getSharedPreferences(key, Context.MODE_PRIVATE)
            val id = sharedPref.getInt(key, METRIC.id)

            return DisplayUnit.values().find { it.id == id }!!
        }
    }
}