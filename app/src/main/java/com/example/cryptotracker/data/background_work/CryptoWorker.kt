package com.example.cryptotracker.data.background_work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.cryptotracker.data.local.preferences.PreferencesDataStore
import com.example.cryptotracker.data.local.room.CryptoDao
import com.example.cryptotracker.data.models.CoinType
import com.example.cryptotracker.data.models.toEntity
import com.example.cryptotracker.data.models.toUiModel
import com.example.cryptotracker.data.network.CryptoApi
import com.example.cryptotracker.data.network.CryptoRepository
import com.example.cryptotracker.utils.Constants
import com.example.cryptotracker.utils.CryptoNotificationManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltWorker
class CryptoWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted params: WorkerParameters,
) : CoroutineWorker(context, params) {

    @Inject
    lateinit var api: CryptoApi

    @Inject
    lateinit var preferencesDataStore: PreferencesDataStore

    @Inject
    lateinit var cryptoNotificationManager: CryptoNotificationManager

    @Inject
    lateinit var cryptoDao: CryptoDao

    override suspend fun doWork(): Result {
        /**
         * Logic
         * 1)fetch preferences
         * 2)compare each crypto rating with prefs
         * 3)if anything in range, alert with notification
         * 4)insert fetched coins in room db
         */

        try {
            val response = api.getCoins(
                coins = listOf(Constants.BITCOIN, Constants.RIPPLE, Constants.ETHEREUM).joinToString(","),
                currencies = Constants.USD
            ).toUiModel()

            Log.d("mytag", "doWork: started ${response}")

            response.coins.forEach {
                val minMaxValue = preferencesDataStore.getMinMaxValues(it).first()
                if (minMaxValue != null) {
                    if (it.usd in (minMaxValue.min)..(minMaxValue.max)) {
                        cryptoNotificationManager.showCryptoRateChanged(
                            notificationId = it.type.ordinal,
                            message = "${it.name} rate is in the range - [${minMaxValue.min},${minMaxValue.max}]"
                        )
                    }
                }
                cryptoDao.insertCoin(it.toEntity())
            }

            return Result.success()
        } catch (e: Exception) {
            Log.d("mytag", "${e.message}")
            return Result.failure()
        }

    }


}