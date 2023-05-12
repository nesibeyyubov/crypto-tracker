package com.example.cryptotracker.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.cryptotracker.data.models.Coin
import com.example.cryptotracker.data.models.CoinType
import com.example.cryptotracker.utils.Constants.BITCOIN
import com.example.cryptotracker.utils.Constants.ETHEREUM
import com.example.cryptotracker.utils.Constants.KEY_MAX
import com.example.cryptotracker.utils.Constants.KEY_MIN
import com.example.cryptotracker.utils.Constants.RIPPLE
import com.example.cryptotracker.utils.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataStore @Inject constructor(@ApplicationContext context: Context) {

    companion object {
        const val MIN_MAX_SEPARATOR = "|"
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    private val bitcoinKey = stringPreferencesKey(BITCOIN)
    private val ethereumKey = stringPreferencesKey(ETHEREUM)
    private val rippleKey = stringPreferencesKey(RIPPLE)


    suspend fun setMinMax(coin: Coin, min: Float, max: Float) {
        dataStore.edit {
            val key = when (coin.type) {
                CoinType.Bitcoin -> bitcoinKey
                CoinType.Ethereum -> ethereumKey
                CoinType.Ripple -> rippleKey
            }
            it[key] = "$min$MIN_MAX_SEPARATOR$max"
        }
    }

    fun getMinMaxValues(coin: Coin): Flow<MinMaxRate> {
        return dataStore.data.map {
            val minMaxString = when (coin.type) {
                CoinType.Bitcoin -> it[bitcoinKey]
                CoinType.Ethereum -> it[ethereumKey]
                CoinType.Ripple -> it[rippleKey]
            }
            val minMaxValues = minMaxString?.split(MIN_MAX_SEPARATOR)?.map { str -> str.toFloat() } ?: emptyList()
            if (minMaxValues.isNotEmpty()) {
                return@map MinMaxRate(minMaxValues[0], minMaxValues[1])
            } else {
                return@map MinMaxRate(0f, 0f)
            }
        }
    }

}


data class MinMaxRate(
    val min: Float,
    val max: Float
)