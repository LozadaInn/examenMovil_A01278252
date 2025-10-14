package com.app.examenmovil.data.local

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // ultimo país visitado
    companion object {
        private const val KEY_LAST_VISITED_COUNTRY = "last_visited_country"
    }

    fun saveLastVisitedCountry(countryName: String?) {
        sharedPreferences.edit().putString(KEY_LAST_VISITED_COUNTRY, countryName).apply()
    }

    /*
     * Lee el nombre del último país visitado.
     * Si no encuentra nada, devuelve null.
     */
    fun getLastVisitedCountry(): String? {
        return sharedPreferences.getString(KEY_LAST_VISITED_COUNTRY, null)
    }
}
