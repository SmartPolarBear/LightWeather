package com.smartpolarbear.lightweather.settings

import android.content.Context
import androidx.annotation.StringRes
import com.smartpolarbear.lightweather.R

/**
 * @param id the id, which is written to shared preference
 * @param displayNameResId the name to be displayed in settings UI
 */
enum class DisplayUnit(val id: Int, @StringRes val displayNameResId: Int) {
    METRIC(id = 1, displayNameResId = R.string.metric),
    IMPERIAL(id = 1, displayNameResId = R.string.imperial);

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