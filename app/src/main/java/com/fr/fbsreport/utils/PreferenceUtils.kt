package com.fr.fbsreport.utils

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.fr.fbsreport.App

class PreferenceUtils(app: App) {

    private var sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(app)

    fun removeKey(key: String) {
        if (sharedPreferences.contains(key)) sharedPreferences.edit().remove(key).apply()
    }

    fun clearData() {
        sharedPreferences.edit().clear().apply()
    }

    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun putInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }
}