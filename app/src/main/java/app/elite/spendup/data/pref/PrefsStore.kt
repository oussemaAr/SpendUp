package app.elite.spendup.data.pref

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import app.elite.spendup.utils.Constants.STORE_NAME
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class PrefsStore(context: Context) {
    private val dataStore = context.createDataStore(
        STORE_NAME
    )

    fun isNightMode() = dataStore.data.catch { exception ->
        if (exception is IOException)
            emit(emptyPreferences())
        else
            throw exception
    }.map {
        it[PreferencesKeys.NIGHT_MODE_KEY] ?: false
    }

    fun getUsername() = dataStore.data.catch { exception ->
        if (exception is IOException)
            emit(emptyPreferences())
        else
            throw exception
    }.map {
        it[PreferencesKeys.USERNAME_KEY]
    }

    suspend fun setUsername(username: String) {
        dataStore.edit {
            it[PreferencesKeys.USERNAME_KEY] = username
        }
    }

    suspend fun toggleNightMode() {
        dataStore.edit {
            it[PreferencesKeys.NIGHT_MODE_KEY] = !(it[PreferencesKeys.NIGHT_MODE_KEY] ?: false)
        }
    }

}

private object PreferencesKeys {
    val NIGHT_MODE_KEY = booleanPreferencesKey("dark_theme_enable")
    val USERNAME_KEY = stringPreferencesKey("username")
}
