package ru.yellowshark.dsr_weather.data.other

import android.content.Context
import android.content.SharedPreferences
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.utils.METRIC_UNITS

class UnitManager(
    context: Context
) {
    companion object {
        private const val MEASURE_KEY = "MEASURE_KEY"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    fun setUnit(measure: String) {
        val editor = prefs.edit()
        editor.putString(MEASURE_KEY, measure)
        editor.apply()
    }

    fun getUnit(): String {
        return prefs.getString(MEASURE_KEY, METRIC_UNITS) ?: METRIC_UNITS
    }

    fun getUnitSymbol(): String {
        return if (getUnit() == METRIC_UNITS) "°C" else "°F"
    }
}