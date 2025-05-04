package uk.org.amey.mobile.timestables.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val PREFERENCES_NAME = "preferences"

val Context.dataStore by preferencesDataStore(
    name = PREFERENCES_NAME
)

@Singleton
class PreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore: DataStore<Preferences> = context.dataStore

    private object Keys {
        val MAX_STREAK = intPreferencesKey("max_streak")
    }

    suspend fun setMaxStreak(streak: Int) {
        dataStore.edit { it[Keys.MAX_STREAK] = streak }
    }

    val maxStreak: Flow<Int> = dataStore.data.map { it[Keys.MAX_STREAK] ?: 0 }
}