package com.dicoding.submission1_fundamentalandroid.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Settings private constructor(private val dataStore: DataStore<Preferences>) {

    private val key = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[key] ?: false
        }
    }

    suspend fun saveThemeSetting(isDark: Boolean) {
        dataStore.edit { preferences ->
            preferences[key] = isDark
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: Settings? = null

        fun getInstance(dataStore: DataStore<Preferences>): Settings {
            return INSTANCE ?: synchronized(this) {
                val instance = Settings(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}