package ir.mhira.donow.data.preferences

import android.content.Context
import kotlinx.coroutines.flow.Flow
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import ir.mhira.donow.Constants
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = Constants.DATA_STORE_NAME)

private val DARK_THEME_KEY = booleanPreferencesKey(Constants.APP_DARK_THEME_KEY)

class SettingsDataStore(private val context: Context) {

    suspend fun setDarkTheme(enabled: Boolean) {
        context.dataStore.edit {
            it[DARK_THEME_KEY] = enabled
        }
    }

    val darkTheme: Flow<Boolean> =
        context.dataStore.data.map {
            it[DARK_THEME_KEY] ?: false
        }
}