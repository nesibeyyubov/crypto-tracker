package com.example.cryptotracker.ui.min_max

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.cryptotracker.base.BaseViewModel
import com.example.cryptotracker.data.background_work.CryptoWorker
import com.example.cryptotracker.data.local.preferences.PreferencesDataStore
import com.example.cryptotracker.data.models.Coin
import com.example.cryptotracker.utils.Constants.CRYPTO_WORKER_NAME
import com.example.cryptotracker.utils.CryptoNotificationManager
import com.example.cryptotracker.utils.inMoneyFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MinMaxViewmodel @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore,
    private val notificationManager: CryptoNotificationManager
) : BaseViewModel<MinMaxState>(MinMaxState()) {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.d("ERROR", throwable.message.toString())
    }

    fun saveMinMax(coin: Coin, min: Float, max: Float) {
        notificationManager.showBackgroundWorkStarted(
            "You will receive notification when ${coin.name} is between range [${min.inMoneyFormat()}$-${max.inMoneyFormat()}$]"
        )

        viewModelScope.launch(coroutineExceptionHandler) {
            preferencesDataStore.setMinMax(coin, min, max)
        }
    }

    fun getMinMax(coin: Coin) {
        preferencesDataStore.getMinMaxValues(coin)
            .onStart { setState { it.copy(loading = true) } }
            .onEach { rate -> setState { it.copy(loading = false, minMax = rate) } }
            .catch { }
            .launchIn(viewModelScope)
    }

}